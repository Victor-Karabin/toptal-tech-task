package com.toptal.github.presentation.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RepositoryDetailsViewModel : ViewModel() {

    val state = MutableStateFlow<UiRepositoryDetails?>(value = null)
}
