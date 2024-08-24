package com.toptal.github.presentation.details

data class UiRepositoryDetails(
    val title: String,
    val content: Content,
) {

    sealed class Content {

        data object Loading : Content()

        data class Loaded(
            val countOpenedIssues: Int,
            val countClosedIssues: Int,
            val openedIssuesTitles: String,
            val countOpenPrs: Int,
            val countClosedPrs: Int,
            val openedPrsTitles: String,
        ) : Content()

        data class FullScreenError(val onRetryClicked: () -> Unit) : Content()
    }
}
