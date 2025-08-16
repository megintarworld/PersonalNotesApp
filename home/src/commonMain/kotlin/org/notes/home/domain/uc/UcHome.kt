package org.notes.home.domain.uc

import kotlinx.coroutines.flow.Flow
import org.notes.core.domain.model.Note
import org.notes.home.domain.repo.RepoHome

class UcHome(private val repo: RepoHome) {

    suspend fun saveNotes(note: Note) {
        repo.saveNotes(note)
    }

    fun getNotes(): Flow<List<Note>> {
        return repo.getNotes()
    }

    suspend fun deleteNoteById(id: String) {
        repo.deleteNoteById(id)
    }

    suspend fun deleteNote(note: Note) {
        repo.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        repo.updateNote(note)
    }

    fun getNoteById(id: String): Flow<Note?> {
        return repo.getNote(id)
    }


}