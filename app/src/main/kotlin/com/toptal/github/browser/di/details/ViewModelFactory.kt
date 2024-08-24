package com.toptal.github.browser.di.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.toptal.data.networking.Api
import com.toptal.github.presentation.details.RepositoryDetailsViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val api: Api,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        require(modelClass == RepositoryDetailsViewModel::class.java)
        val repoId = requireNotNull(extras[RepositoryDetailsViewModel.Companion.RepoId])
        val repoTitle = requireNotNull(extras[RepositoryDetailsViewModel.Companion.RepoTitle])

        return RepositoryDetailsViewModel(api, repoId, repoTitle) as T
    }
}
