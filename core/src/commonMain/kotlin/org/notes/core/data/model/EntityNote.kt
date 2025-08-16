package org.notes.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.notes.core.domain.model.Note

@Entity
data class EntityNote(
    @PrimaryKey val id: String,
    val title: String,
    val htmlBody: String,
    val createdDate: String,
    val isPinned: Boolean =false
)

fun EntityNote.toNote(): Note {
    return Note(
        id = id,
        title = title,
        htmlBody = htmlBody,
        createdDate = createdDate,
        isPinned = isPinned
    )
}