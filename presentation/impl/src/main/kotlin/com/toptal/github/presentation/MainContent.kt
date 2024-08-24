package com.toptal.github.presentation

import androidx.compose.runtime.Composable

@Composable
fun MainContent(content: @Composable () -> Unit) {
    // setup theme, local providers, etc
    content()
}
