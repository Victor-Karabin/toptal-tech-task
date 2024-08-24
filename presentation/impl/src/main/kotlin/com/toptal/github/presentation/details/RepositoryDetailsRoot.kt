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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toptal.github.presentation.details.UiRepositoryDetails.Content
import com.toptal.github.presentation.impl.R

@Composable
fun RepositoryDetailsRoot(
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailsViewModel,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchDetails()
    }

    val model by viewModel.state.collectAsState()

    RepositoryDetailsRoot(
        modifier = modifier,
        model = model,
        onClickBack = onBack,
    )
}

@Composable
private fun RepositoryDetailsRoot(
    model: UiRepositoryDetails?,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (model != null) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onClickBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.common_back),
                            )
                        }
                    },
                    title = { Text(text = model.title) },
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
        ) { innerPadding ->
            val contentModifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)

            when (val content = model.content) {
                is Content.FullScreenError -> FullScreenError(
                    modifier = contentModifier,
                    model = content,
                )

                is Content.Loaded -> Content(modifier = contentModifier, model = content)

                Content.Loading -> Loading(modifier = contentModifier)
            }
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
        val openedTitles = stringResource(id = R.string.opened_titles)

        val issues = stringResource(id = R.string.issues)
        val openedIssues = stringResource(id = R.string.opened_issues_count, model.countOpenedIssues)
        val closedIssues = stringResource(id = R.string.closed_issues_count, model.countClosedIssues)

        val prs = stringResource(id = R.string.pull_requests)
        val openedPrs = stringResource(id = R.string.opened_pull_requests_count, model.countOpenPrs)
        val closedPrs = stringResource(id = R.string.closed_pull_requests_count, model.countClosedPrs)

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    appendLine("$issues:")
                }
                appendLine(openedIssues)
                appendLine(closedIssues)
                appendLine("$openedTitles:")
                appendLine(model.openedIssuesTitles)

                appendLine()

                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    appendLine("$prs:")
                }
                appendLine(openedPrs)
                appendLine(closedPrs)
                appendLine("$openedTitles:")
                appendLine(model.openedPrsTitles)
            },
        )
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
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
        onClickBack = {},
    )
}

@Preview
@Composable
private fun RepositoryDetailsLoadingPreview() {
    RepositoryDetailsRoot(
        model = UiRepositoryDetails(
            title = "Fixture Repository Name",
            content = Content.Loading,
        ),
        onClickBack = {},
    )
}

@Preview
@Composable
private fun RepositoryDetailsPreview() {
    RepositoryDetailsRoot(
        model = UiRepositoryDetails(
            title = "Fixture Repository Name",
            content = Content.Loaded(
                countOpenedIssues = 1,
                countClosedIssues = 3,
                openedIssuesTitles = "Issue1, Issue2",
                countOpenPrs = 4,
                countClosedPrs = 10,
                openedPrsTitles = "Pull Request1, Pull Request2",
            ),
        ),
        onClickBack = {},
    )
}
