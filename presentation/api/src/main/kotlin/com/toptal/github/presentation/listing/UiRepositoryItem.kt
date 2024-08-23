package com.toptal.github.presentation.listing

data class UiRepositoryList(
    val items: List<UiRepositoryItem>,
)

sealed class UiRepositoryItem {

    data object Progress : UiRepositoryItem()

    data class Error(val onRetryClicked: () -> Unit) : UiRepositoryItem()

    data class Repository(
        val name: String,
        val url: String,
        val onClicked: () -> Unit,
    ) : UiRepositoryItem()
}
