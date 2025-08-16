package org.notes.home.domain.repo

import kotlinx.coroutines.flow.Flow
import org.notes.core.domain.model.Note

interface RepoHome {
    suspend fun saveNotes(note: Note)
    fun getNotes(): Flow<List<Note>>
    suspend fun deleteNoteById(id: String)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    fun getNote(id: String): Flow<Note?>
}