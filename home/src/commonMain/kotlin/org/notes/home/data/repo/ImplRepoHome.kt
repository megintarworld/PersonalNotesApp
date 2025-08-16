package org.notes.home.data.repo

import kotlinx.coroutines.flow.Flow
import org.notes.core.data.ServiceCrudNote
import org.notes.core.domain.model.Note
import org.notes.home.domain.repo.RepoHome

class ImplRepoHome(
    private val serviceCrudNote: ServiceCrudNote
): RepoHome {
    override suspend fun saveNotes(note: Note) {
        serviceCrudNote.insert(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return serviceCrudNote.getData()
    }

    override suspend fun deleteNoteById(id: String) {
       serviceCrudNote.deleteById(id)
    }

    override suspend fun deleteNote(note: Note) {
        serviceCrudNote.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        serviceCrudNote.update(note)
    }

    override fun getNote(id: String): Flow<Note?> {
        return serviceCrudNote.getDataById(id)
    }

}