package com.toptal.github.browser.di.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toptal.data.networking.Api
import com.toptal.github.presentation.listing.RepositoriesListViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val api: Api,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == RepositoriesListViewModel::class.java)
        return RepositoriesListViewModel(api) as T
    }
}
