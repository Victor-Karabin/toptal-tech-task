package com.toptal.github.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.toptal.data.networking.Api
import com.toptal.data.networking.ApiRepositoryRequest
import com.toptal.github.presentation.listing.RepositoriesListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    private val api: Api,
    private val repoId: String,
    private val repoTitle: String,
) : ViewModel() {

    private val mutableState = MutableStateFlow<UiRepositoryDetails?>(value = null)
    internal val state = mutableState.asStateFlow()

    internal fun fetchDetails() {
        viewModelScope.launch {
            mutableState.update { UiRepositoryDetails(title = repoTitle, content = UiRepositoryDetails.Content.Loading) }

            mutableState.update {
                try {
                    val request = ApiRepositoryRequest(repoId, ISSUES_LIMIT, PRS_LIMIT)
                    val details = api.getRepositoryDetails(request)
                    val loaded = details.toContent()
                    UiRepositoryDetails(title = repoTitle, content = loaded)
                } catch (ex: Exception) {
                    Log.d(TAG, "fetch items is failed", ex)
                    UiRepositoryDetails(title = repoTitle, content = UiRepositoryDetails.Content.FullScreenError { fetchDetails() })
                }
            }
        }
    }

    companion object {

        private val TAG = RepositoriesListViewModel::class.java.name
        private const val ISSUES_LIMIT = 10
        private const val PRS_LIMIT = 10

        object RepoId : CreationExtras.Key<String>
        object RepoTitle : CreationExtras.Key<String>
    }
}
