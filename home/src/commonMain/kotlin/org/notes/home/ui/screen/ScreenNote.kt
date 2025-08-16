package org.notes.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.notes.core.domain.Res
import org.notes.core.domain.log
import org.notes.core.domain.model.Note
import org.notes.home.domain.vm.VmHome
import org.notes.home.ui.comp.CmpDatePicker
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ScreenNote(
    onNav: (Any) -> Unit,
    noteId: String? = null
) {

    val vmHome = koinViewModel<VmHome>()
    var showDatePicker by remember { mutableStateOf(false) }

    val note by noteId?.let {
        vmHome.getNoteById(it).collectAsStateWithLifecycle(initialValue = null)
    } ?: remember { mutableStateOf<Note?>(null) }

    log("NoteId: $note")

    var selectedDate by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    }
    var title by remember { mutableStateOf("") }
    var htmlBody by remember { mutableStateOf("") }

    LaunchedEffect(note) {
        note?.let {
            selectedDate = LocalDate.parse(it.createdDate)
            title = it.title
            htmlBody = it.htmlBody
        }
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Create Note",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Button(
                onClick = {
                    val noteToSave = Note(
                        id = note?.id ?: Clock.System.now().toEpochMilliseconds().toString(),
                        title = title,
                        htmlBody = htmlBody,
                        createdDate = selectedDate.toString()
                    )

                    if (noteId == null) vmHome.saveNotes(noteToSave)
                    else vmHome.updateNote(noteToSave)
                    onNav("1")
                },
                enabled = title.isNotBlank() && htmlBody.isNotBlank(),
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(if (noteId == null) "Save" else "Update")
            }
        }

        Spacer(modifier = Modifier.size(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Created Date",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
                val reversedDate = selectedDate.toString()
                    .split("-")
                    .reversed()
                    .joinToString("-")
                Text(
                    text = reversedDate,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
            }
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    modifier = Modifier.size(30.dp).alpha(0.6f),
                    painter = painterResource(Res.Icon.CALENDAR),
                    contentDescription = null
                )
            }
        }

        // Pass states down
        CreateNote(
            title = title,
            onTitleChange = { title = it },
            htmlBody = htmlBody,
            onBodyChange = { htmlBody = it }
        )

        if (showDatePicker) {
            CmpDatePicker(
                initialDate = selectedDate,
                onDateSelected = {
                    selectedDate = it
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}

@Composable
private fun CreateNote(
    title: String,
    onTitleChange: (String) -> Unit,
    htmlBody: String,
    onBodyChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text("Title", color = MaterialTheme.colorScheme.primary)
            },
            textStyle = MaterialTheme.typography.titleMedium,
            singleLine = true
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = htmlBody,
            onValueChange = onBodyChange,
            label = { Text("Body", color = MaterialTheme.colorScheme.primary) },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize(),
            singleLine = false
        )
    }
}
