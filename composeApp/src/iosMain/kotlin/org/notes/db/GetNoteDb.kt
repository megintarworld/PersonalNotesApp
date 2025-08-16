package org.notes.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun iosNoteDbBuilder(): RoomDatabase.Builder<DbNote> {
    val dbFilePath = docDirectory() + "/notes.db"

    return Room.databaseBuilder<DbNote>(
        name = dbFilePath
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun docDirectory(): String{
    val docDir = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(docDir?.path)
}