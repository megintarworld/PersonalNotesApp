package org.notes.core.routes

import kotlinx.serialization.Serializable

sealed interface RouteHome {
    @Serializable
    object Home: RouteHome

    @Serializable
    object Dashboard: RouteHome
}