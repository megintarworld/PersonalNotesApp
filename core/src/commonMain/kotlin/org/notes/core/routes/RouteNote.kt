package org.notes.core.routes

import kotlinx.serialization.Serializable


sealed interface RouteNote {
    @Serializable
    object New : RouteNote
    @Serializable
    data class View(val noteId: String) : RouteNote
}