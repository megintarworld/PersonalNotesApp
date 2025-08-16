package org.notes.di.contract

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.notes.core.data.ServiceCrudNote
import org.notes.core.data.model.toNote
import org.notes.core.domain.model.Note
import org.notes.core.domain.model.toEntityNote
import org.notes.db.DbNote

class ServCrudNote(
    private val db: DbNote
) : ServiceCrudNote {
    private val dispatcher = Dispatchers.IO

    override suspend fun insert(data: Note) {
        withContext(dispatcher) {
            db.daoNote().insertNote(data.toEntityNote())
        }
    }

    override suspend fun update(data: Note) {
        withContext(dispatcher) {
            db.daoNote().updateNote(data.toEntityNote())
        }
    }

    override fun getData(): Flow<List<Note>> {
        return db.daoNote().getNotes().map { list ->
            list.map { it.toNote() }
        }
    }

    override fun getDataById(id: String): Flow<Note?> {
        return db.daoNote().getNote(id).map { it?.toNote() }
    }

    override suspend fun delete(data: Note) {
        withContext(dispatcher) {
            db.daoNote().deleteNote(data.toEntityNote())
        }
    }

    override suspend fun deleteById(id: String) {
        withContext(dispatcher) {
            db.daoNote().deleteNoteById(id)
        }
    }

}