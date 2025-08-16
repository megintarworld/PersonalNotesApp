package org.notes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.notes.core.data.model.EntityNote

@Dao
interface DaoNote {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: EntityNote)

    @Update
    suspend fun updateNote(note: EntityNote)

    @Query("SELECT * FROM EntityNote ORDER BY createdDate DESC")
    fun getNotes(): Flow<List<EntityNote>>

    @Query("SELECT * FROM EntityNote WHERE id = :id")
    fun getNote(id: String): Flow<EntityNote?>

    @Query("DELETE FROM EntityNote WHERE id = :id")
    suspend fun deleteNoteById(id: String)

    @Delete
    fun deleteNote(note: EntityNote)
}