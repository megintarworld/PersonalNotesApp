package org.notes.home.ui.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.notes.core.domain.Res
import org.notes.core.domain.model.Note
import org.notes.home.ui.contract.ItemClickType

@Preview
@Composable
internal fun CmpNoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onItemNav: (Note) -> Unit,
    onItemDelete: (Note) -> Unit,
    onItemHtmlView: (String) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 1.dp, vertical = 5.dp)
            .background(color = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onItemNav(note) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = note.createdDate,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.size(8.dp)) // space between date and icon
                        IconButton(
                            modifier = Modifier.padding(5.dp),
                            onClick = { onItemHtmlView(note.htmlBody) }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp).alpha(0.2f),
                                painter = painterResource(Res.Icon.HTML),
                                contentDescription = null
                            )
                        }
                        IconButton(
                            modifier = Modifier.padding(5.dp),
                            onClick = { onItemDelete(note) }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp).alpha(0.2f),
                                painter = painterResource(Res.Icon.DELETE),
                                contentDescription = null
                            )
                        }
                    }
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = note.htmlBody,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

