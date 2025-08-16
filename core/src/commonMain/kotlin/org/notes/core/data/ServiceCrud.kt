package org.notes.core.data

import kotlinx.coroutines.flow.Flow
import org.notes.core.domain.model.Note

interface ServiceCrudNote {
    suspend fun insert(data: Note)
    suspend fun update(data: Note)
    fun getData(): Flow<List<Note>>
    fun getDataById(id: String): Flow<Note?>
    suspend fun delete(data: Note)
    suspend fun deleteById(id: String)
}