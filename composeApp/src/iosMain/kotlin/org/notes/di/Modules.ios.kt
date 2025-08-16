package org.notes.di

import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.notes.db.DbNote
import org.notes.db.iosNoteDbBuilder

actual val moduleTarget: Module = module {
    single<RoomDatabase.Builder<DbNote>> { iosNoteDbBuilder() }
}