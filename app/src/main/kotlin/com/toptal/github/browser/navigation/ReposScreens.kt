package com.toptal.github.browser.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.toptal.github.browser.di.details.DaggerDetailsComponent
import com.toptal.github.browser.di.listing.DaggerListingComponent
import com.toptal.github.browser.di.networkingComponent
import com.toptal.github.presentation.details.RepositoryDetailsRoot
import com.toptal.github.presentation.details.RepositoryDetailsViewModel
import com.toptal.github.presentation.listing.RepositoriesListRoot
import com.toptal.github.presentation.listing.RepositoriesListViewModel

private const val PARAM_REPO_ID = "repo_id"
private const val PARAM_REPO_TITLE = "repo_title"

internal enum class RepoScreens {
    List,
    Details
}

internal fun RepoScreens.toPath(): String {
    return "users/" + when (this) {
        RepoScreens.List -> "list"
        RepoScreens.Details -> "details"
    }
}

internal fun RepoScreens.toRoute(): String {
    return this.toPath() + when (this) {
        RepoScreens.List -> ""
        RepoScreens.Details -> "?$PARAM_REPO_ID={$PARAM_REPO_ID}&$PARAM_REPO_TITLE={$PARAM_REPO_TITLE}"
    }
}

internal fun NavGraphBuilder.reposNavGraph(navController: NavController) {
    composable(route = RepoScreens.List.toRoute()) {

        val viewModel = viewModel(
            modelClass = RepositoriesListViewModel::class.java,
            factory = DaggerListingComponent.builder()
                .networkingComponent(LocalContext.current.networkingComponent())
                .build()
                .viewModelFactory(),
        )

        RepositoriesListRoot(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel,
            onNavigateDetails = { repoId, repoTitle ->
                val target = RepoScreens.Details.toPath() + "?$PARAM_REPO_ID=$repoId&$PARAM_REPO_TITLE=$repoTitle"
                navController.navigate(target)
            },
        )
    }

    composable(
        route = RepoScreens.Details.toRoute(),
        arguments = listOf(navArgument(PARAM_REPO_ID) { type = NavType.StringType }),
    ) { backStack ->
        val repoId = backStack.arguments?.getString(PARAM_REPO_ID)
        requireNotNull(repoId) { "nav param $PARAM_REPO_ID not found" }

        val repoTitle = backStack.arguments?.getString(PARAM_REPO_TITLE)
        requireNotNull(repoTitle) { "nav param $PARAM_REPO_TITLE not found" }

        val extras: CreationExtras = MutableCreationExtras().apply {
            set(RepositoryDetailsViewModel.Companion.RepoId, repoId)
            set(RepositoryDetailsViewModel.Companion.RepoTitle, repoTitle)
        }

        val viewModel = viewModel(
            modelClass = RepositoryDetailsViewModel::class.java,
            key = RepositoryDetailsViewModel::class.java.name + "#$repoId",
            extras = extras,
            factory = DaggerDetailsComponent.builder()
                .networkingComponent(LocalContext.current.networkingComponent())
                .build()
                .viewModelFactory(),
        )

        RepositoryDetailsRoot(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
        )
    }
}
