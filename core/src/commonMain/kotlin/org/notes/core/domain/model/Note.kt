package org.notes.core.domain.model

import kotlinx.serialization.Serializable
import org.notes.core.data.model.EntityNote

@Serializable
data class Note(
    val id: String,
    val title: String,
    val htmlBody: String,
    val createdDate: String,
    val isPinned: Boolean =false
)

fun Note.toEntityNote(): EntityNote {
    return EntityNote(
        id = id,
        title = title,
        htmlBody = htmlBody,
        createdDate = createdDate,
        isPinned = isPinned
    )
}