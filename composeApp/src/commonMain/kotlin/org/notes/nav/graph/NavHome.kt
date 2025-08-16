package org.notes.nav.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.notes.core.routes.RouteHome
import org.notes.core.routes.RouteNote
import org.notes.core.routes.RouteUiRender
import org.notes.home.ui.screen.ScreenHome
import org.notes.home.ui.screen.ScreenNote
import org.notes.ui.ScreenHtmlView
import org.notes.ui.ScreenPdfView


internal fun NavGraphBuilder.navHome(navC: NavHostController) {

    composable<RouteHome.Home> {
        ScreenHome(
            onNav = { navC.navigate(it) },
            onNavNote = { id -> navC.navigate(RouteNote.View(id)) }
        )
    }
    composable<RouteNote.View> {
        val noteId = it.toRoute<RouteNote.View>().noteId
        ScreenNote(
            noteId = noteId,
            onNav = { navC.navigateUp() }
        )
    }
    composable<RouteNote.New> {
        ScreenNote(
            onNav = { navC.navigateUp() }
        )
    }

    composable<RouteUiRender.HtmlView> {
        val html = it.toRoute<RouteUiRender.HtmlView>().html
        ScreenHtmlView(
            html = html,
            onNav = { navC.navigateUp() }
        )
    }

    composable<RouteUiRender.PdfView> {
        ScreenPdfView(
            onNav = { navC.navigateUp() }
        )
    }

}