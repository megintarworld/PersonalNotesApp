package org.notes.home.domain.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.notes.core.domain.model.Note
import org.notes.home.domain.uc.UcHome

class VmHome(
    private val ucNotes: UcHome
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        viewModelScope.launch {
            ucNotes.getNotes().collect { list ->
                _notes.value = list
            }
        }
    }

    fun saveNotes(note: Note) {
        viewModelScope.launch {
            ucNotes.saveNotes(note)
        }
    }

    fun deleteNoteById(id: String) {
        viewModelScope.launch {
            ucNotes.deleteNoteById(id)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            ucNotes.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            ucNotes.updateNote(note)
        }
    }

    fun getNoteById(id: String): Flow<Note?> = ucNotes.getNoteById(id)


}