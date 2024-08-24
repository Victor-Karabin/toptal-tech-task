package com.toptal.github.presentation.listing

import com.toptal.data.networking.ApiRepository

internal fun ApiRepository.toItem(): UiRepositoryItem {
    return UiRepositoryItem.Repository(
        id = this.id,
        name = this.name,
        url = this.url,
    )
}
