package org.notes.di.contract

import androidx.compose.runtime.Composable

@Composable
expect fun HtmlViewer(
    html: String,
    onButtonClick: (String) -> Unit
)

@Composable
expect fun PdfViewer(
    url: String,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
)