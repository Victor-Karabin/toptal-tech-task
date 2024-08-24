package com.toptal.github.presentation.listing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.data.networking.Api
import com.toptal.data.networking.ApiRepositoriesRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RepositoriesListViewModel(
    private val api: Api,
) : ViewModel() {

    private val mutableState = MutableStateFlow<UiRepositoryList?>(value = null)
    internal val state = mutableState.asStateFlow()

    internal fun fetchRepos() {
        viewModelScope.launch {
            mutableState.update {
                UiRepositoryList(
                    items = List(1) {
                        UiRepositoryItem.Progress
                    },
                )
            }

            mutableState.update {
                try {
                    val request = ApiRepositoriesRequest(OWNER, LIMIT)
                    val items = api.getRepositories(request)
                        .map { repo -> repo.toItem() }

                    UiRepositoryList(items = items)
                } catch (ex: Exception) {
                    Log.d(TAG, "fetch items is failed", ex)
                    UiRepositoryList(items = List(1) { UiRepositoryItem.Error { fetchRepos() } })
                }
            }
        }
    }

    private companion object {

        private val TAG = RepositoriesListViewModel::class.java.name
        private const val OWNER = "Victor-Karabin"
        private const val LIMIT = 40
    }
}
