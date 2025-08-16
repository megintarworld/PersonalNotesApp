package org.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.notes.core.domain.Res
import org.notes.di.contract.PdfViewer

@Composable
fun ScreenPdfView(
    onNav: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onNav() }) {
                Icon(
                    painter = painterResource(Res.Icon.BACK),
                    contentDescription = "Back",
                    modifier = Modifier.size(34.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PDF Viewer",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        PdfViewer(
            url = "https://qa.pilloo.ai/GeneratedPDF/Companies/202/2025-2026/DL.pdf",
            modifier = Modifier.weight(1f)
        )
    }
}