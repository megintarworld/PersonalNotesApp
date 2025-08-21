package org.notes.di.contract

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import org.notes.core.domain.log
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.graphics.createBitmap

class JsBridge(private val onClick: (String) -> Unit) {
    @JavascriptInterface
    fun onButtonClicked(text: String) {
        onClick(text)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun HtmlViewer(
    html: String, onButtonClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                addJavascriptInterface(
                    JsBridge { text ->
                        Toast.makeText(ctx, "Clicked: $text", Toast.LENGTH_SHORT).show()
                        onButtonClick(text)
                    },
                    "AndroidBridge"
                )

                val htmlWithJs = """
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript">
            function setupClickHandlers() {
                document.querySelectorAll('button, a').forEach(function(el) {
                    el.addEventListener('click', function(event) {
                        event.preventDefault();
                        AndroidBridge.onButtonClicked(this.id || this.innerText);
                    });
                });
            }
        </script>
    </head>
    <body onload="setupClickHandlers()">
        $html
    </body>
    </html>
""".trimIndent()

                loadDataWithBaseURL(null, htmlWithJs, "text/html", "utf-8", null)
            }
        })
    }
}

@Composable
actual fun PdfViewer(
    url: String,
    modifier: Modifier
) {
    log("Android PdfViewer: $url")

//    LibPdf(url, modifier)
    CustomPdf(url, modifier)
}
@Composable
private fun LibPdf(url: String, modifier: Modifier) {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(url),
        isZoomEnable = true
    )

    Box(
        modifier = modifier.fillMaxSize().background(Color.White).padding(1.dp),
    ){
        VerticalPDFReader(
            state = pdfState,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CustomPdf(url: String, modifier: Modifier) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Load PDF and render first page into a bitmap
    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connect()
                val file = File.createTempFile("temp", ".pdf", context.cacheDir)
                file.outputStream().use { out ->
                    connection.inputStream.use { input ->
                        input.copyTo(out)
                    }
                }
                val pdfRenderer = PdfRenderer(
                    ParcelFileDescriptor.open(
                        file,
                        ParcelFileDescriptor.MODE_READ_ONLY
                    )
                )
                val page = pdfRenderer.openPage(0) // just first page for now
                val bmp = createBitmap(page.width, page.height)
                page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                pdfRenderer.close()

                bitmap = bmp
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (bitmap != null) {
        ZoomableBitmap(bitmap!!, modifier)
    } else {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
@Composable
fun ZoomableBitmap(
    bitmap: Bitmap,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)

                    val baseScale = screenWidthPx / bitmap.width.toFloat()
                    val scaledWidth = bitmap.width * baseScale * scale
                    val scaledHeight = bitmap.height * baseScale * scale

                    val maxX = maxOf((scaledWidth - screenWidthPx) / 2f, 0f)
                    val maxY = maxOf((scaledHeight - screenHeightPx) / 2f, 0f)

                    val newOffset = offset + pan
                    offset = Offset(
                        x = newOffset.x.coerceIn(-maxX, maxX),
                        y = newOffset.y.coerceIn(-maxY, maxY)
                    )
                }
            }
    ) {
        val baseScale = screenWidthPx / bitmap.width.toFloat()

        // ✅ Base centering (when no pan)
        val baseOffsetX = (screenWidthPx - bitmap.width * baseScale * scale) / 2f
        val baseOffsetY = (screenHeightPx - bitmap.height * baseScale * scale) / 2f

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "PDF Page",
            modifier = Modifier.graphicsLayer(
                scaleX = baseScale * scale,
                scaleY = baseScale * scale,
                translationX = baseOffsetX + offset.x,
                translationY = baseOffsetY + offset.y,
                transformOrigin = TransformOrigin(0f, 0f) // 🔑 scaling anchor
            )
        )
    }
}
