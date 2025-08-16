package org.notes.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.notes.core.domain.log
import org.notes.core.domain.model.Note
import org.notes.home.domain.vm.VmHome
import org.notes.home.ui.comp.CmpNoteCard

@Composable
fun ScreenNotes(
    onNavToNote: (String) -> Unit,
    onNavToHtml: (String) -> Unit
) {
    val vm = koinViewModel<VmHome>()
    val notesList by vm.notes.collectAsStateWithLifecycle()

    Column {

        Notes(
            notes = notesList,
            onItemNav = {
                onNavToNote(it.id)
            },
            onItemDelete = {
                vm.deleteNote(it)
            },
            onItemHtmlView = {
                onNavToHtml(it)
            }
        )

    }
}

@Composable
private fun Notes(
    notes: List<Note>,
    onItemNav: (Note) -> Unit,
    onItemDelete: (Note) -> Unit,
    onItemHtmlView: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(notes) { note ->
            CmpNoteCard(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                note = Note(
                    id = note.id,
                    title = note.title,
                    htmlBody = note.htmlBody,
                    createdDate = note.createdDate
                ),
                onItemNav = onItemNav,
                onItemDelete = onItemDelete,
                onItemHtmlView = onItemHtmlView
            )
        }
    }
}