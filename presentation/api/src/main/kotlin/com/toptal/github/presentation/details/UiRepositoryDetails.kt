package com.toptal.github.presentation.details

data class UiRepositoryDetails(
    val title: String,
    val content: Content,
) {

    sealed class Content {

        data class Loaded(
            val url: String,
        ) : Content()

        data class FullScreenError(val onRetryClicked: () -> Unit) : Content()
    }
}
