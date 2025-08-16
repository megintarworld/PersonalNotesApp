package org.notes.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import org.notes.core.domain.model.Note
import org.notes.core.routes.RouteNote
import org.notes.core.routes.RouteUiRender

@Composable
fun ScreenHome(
    onNav: (Any) -> Unit,
    onNavNote: (String) -> Unit
) {
    Column {
        Home(
            onNav = onNav
        )
        Spacer(modifier = Modifier.size(32.dp))
        ScreenNotes(
            onNavToNote = { onNavNote(it) },
            onNavToHtml = { onNav(RouteUiRender.HtmlView(it)) }
        )
    }
}

@Composable
private fun Home(
    onNav: (Any) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notes",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Row {
                IconButton(onClick = {
                    onNav(RouteUiRender.PdfView)
                }) {
                    Icon(
                        modifier = Modifier.size(34.dp).alpha(0.4f),
                        painter = painterResource(Res.Icon.PDF),
                        contentDescription = null
                    )
                }

                IconButton(onClick = {
                    onNav(RouteNote.New)
                }) {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(Res.Icon.ADD),
                        contentDescription = null
                    )
                }
            }
        }

    }
}
