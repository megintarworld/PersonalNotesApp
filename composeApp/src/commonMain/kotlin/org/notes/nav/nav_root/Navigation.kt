package org.notes.nav.nav_root

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.notes.core.routes.RouteHome
import org.notes.nav.graph.navHome

@Composable
fun Navigation() {
    val navC = rememberNavController()
    NavHost(
        navController = navC,
        startDestination = RouteHome.Home
    ){
        navHome(navC)

    }
}