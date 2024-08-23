package com.toptal.github.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toptal.github.presentation.details.UiRepositoryDetails.Content

@Composable
fun RepositoryDetailsRoot(
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailsViewModel = viewModel(),
) {
    val model by viewModel.state.collectAsState()

    RepositoryDetailsRoot(
        model = model,
        modifier = modifier,
    )
}

@Composable
private fun RepositoryDetailsRoot(
    model: UiRepositoryDetails?,
    modifier: Modifier = Modifier,
) {
    model ?: return
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = model.title) },
            )
        },
        contentWindowInsets = WindowInsets.systemBars,
    ) { innerPadding ->
        val contentModifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding(innerPadding)

        when (val content = model.content) {
            is Content.FullScreenError -> FullScreenError(modifier = contentModifier, model = content)
            is Content.Loaded -> Content(modifier = contentModifier, model = content)
        }
    }
}

@Composable
private fun Content(
    model: Content.Loaded,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("url of the repository=${model.url}")
        Text("counts of open issues, closed issues, open PRs and closed PRs:")
        Text("titles of the open issues and PRs=")
    }
}

@Composable
private fun FullScreenError(
    model: Content.FullScreenError,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Something went wrong…")

            Button(onClick = model.onRetryClicked) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryDetailsErrorPreview() {
    RepositoryDetailsRoot(
        model = UiRepositoryDetails(
            title = "Fixture Repository Name",
            content = Content.FullScreenError(onRetryClicked = { }),
        ),
    )
}

@Preview
@Composable
private fun RepositoryDetailsPreview() {
    RepositoryDetailsRoot(
        model = UiRepositoryDetails(
            title = "Fixture Repository Name",
            content = Content.Loaded(
                url = "https://www.example.com",
            ),
        ),
    )
}
