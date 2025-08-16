package org.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.notes.core.domain.Res
import org.notes.core.domain.log
import org.notes.di.contract.HtmlViewer

@Composable
fun ScreenHtmlView(
    html: String,
    onNav: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = { onNav() }
        ) {
            Icon(
                modifier = Modifier.size(34.dp).alpha(1f),
                painter = painterResource(Res.Icon.BACK),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        HtmlViewer(
            html = html,
            onButtonClick = {
                log("Button clicked: $it")
            }
        )
    }
}
