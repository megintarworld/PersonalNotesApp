package org.notes.di.contract

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.notes.core.domain.log
import java.net.URL

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
                addJavascriptInterface(JsBridge { text ->
                    Toast.makeText(ctx, "Clicked: $text", Toast.LENGTH_SHORT).show()
                    onButtonClick(text)
                }, "AndroidBridge")

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


@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun PdfViewer(
    url: String,
    modifier: Modifier
) {

}