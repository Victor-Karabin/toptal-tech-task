package com.toptal.github.browser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
internal fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = RepoScreens.List.toPath(),
    ) {
        this.reposNavGraph(navController)
    }
}
