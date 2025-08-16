package org.notes.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun androidNoteDbBuilder(ctx: Context): RoomDatabase.Builder<DbNote> {
    val dbFile = ctx.applicationContext.getDatabasePath("notes.db")

    return Room.databaseBuilder<DbNote>(
        ctx,
        dbFile.absolutePath
    )
}