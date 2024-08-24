package com.toptal.github.presentation.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RepositoriesListRoot(
    modifier: Modifier = Modifier,
    viewModel: RepositoriesListViewModel,
    onNavigateDetails: (String, String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchRepos()
    }

    val state by viewModel.state.collectAsState()

    RepositoriesListRoot(
        modifier = modifier,
        model = state,
        onClickItem = { item -> onNavigateDetails(item.id, item.name) },
    )
}

@Composable
private fun RepositoriesListRoot(
    modifier: Modifier = Modifier,
    model: UiRepositoryList?,
    onClickItem: (UiRepositoryItem.Repository) -> Unit,
) {
    if (model != null) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text(text = "Repositories") },
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding,
            ) {
                items(model.items) {
                    ListItem(
                        model = it,
                        onClickItem = onClickItem,
                    )
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    model: UiRepositoryItem,
    onClickItem: (UiRepositoryItem.Repository) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (model) {
        is UiRepositoryItem.Error -> ErrorItem(modifier = modifier, model = model)
        UiRepositoryItem.Progress -> ProgressItem(modifier = modifier)
        is UiRepositoryItem.Repository -> RepoItem(
            modifier = modifier,
            model = model,
            onClickItem = { onClickItem(model) },
        )
    }
}

@Composable
private fun ErrorItem(
    model: UiRepositoryItem.Error,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = "Something went wrong…")

        Button(onClick = model.onRetryClicked) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun ProgressItem(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun RepoItem(
    model: UiRepositoryItem.Repository,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClickItem,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(text = model.name)

            Text(text = model.url)
        }
    }
}

@Preview
@Composable
private fun RepositoriesListPreview() {
    RepositoriesListRoot(
        model = UiRepositoryList(
            items = buildList {
                addAll(
                    List(5) {
                        UiRepositoryItem.Repository(
                            id = it.toString(),
                            name = "index=$it",
                            url = "fixture-url",
                        )
                    },
                )

                add(UiRepositoryItem.Error(onRetryClicked = { }))
                add(UiRepositoryItem.Progress)
            },
        ),
        onClickItem = {},
    )
}
