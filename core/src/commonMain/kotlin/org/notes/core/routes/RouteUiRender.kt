package org.notes.core.routes

import kotlinx.serialization.Serializable

interface RouteUiRender {
    @Serializable
    data class HtmlView(val html: String) : RouteUiRender
    @Serializable
    object PdfView : RouteUiRender
}