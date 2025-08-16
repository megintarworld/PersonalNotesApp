package org.notes.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.notes.core.data.model.EntityNote

@Database(
    entities = [EntityNote::class],
    version = 1,
    exportSchema = true
)

@ConstructedBy(AppDbConstructor::class)
abstract class DbNote: RoomDatabase() {
    abstract fun daoNote(): DaoNote
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDbConstructor: RoomDatabaseConstructor<DbNote>{
    override fun initialize(): DbNote
}