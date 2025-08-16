package org.notes.di

import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.notes.db.DbNote
import org.notes.db.androidNoteDbBuilder

actual val moduleTarget: Module = module {
    single<RoomDatabase.Builder<DbNote>> { androidNoteDbBuilder(androidContext()) }
}