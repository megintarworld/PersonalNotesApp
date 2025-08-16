package org.notes.di.contract

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.PDFKit.PDFDocument
import platform.PDFKit.PDFView
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun HtmlViewer(
    html: String,
    onButtonClick: (String) -> Unit
) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WKWebView(frame = CGRectZero, configuration = WKWebViewConfiguration()).apply {
                loadHTMLString(html, null)
            }
        }
    )
}

@Composable
actual fun PdfViewer(url: String, modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            val pdfView = PDFView(it)
            val nsUrl = NSURL.URLWithString(url)
            val document = PDFDocument(nsUrl)
            pdfView.document = document
            pdfView.autoScales = true
            pdfView
        }
    )
}