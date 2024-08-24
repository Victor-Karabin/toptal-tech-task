package com.toptal.github.presentation.details

import com.toptal.data.networking.ApiRepositoryDetails

internal fun ApiRepositoryDetails.toContent(): UiRepositoryDetails.Content.Loaded {
    val openedIssues = this.issues.filter { issue -> issue.isOpen }
    val openedPrs = this.pullRequests.filter { issue -> issue.isOpen }

    return UiRepositoryDetails.Content.Loaded(
        countOpenedIssues = openedIssues.size,
        countClosedIssues = this.issues.size - openedIssues.size,
        openedIssuesTitles = openedIssues.joinToString("\n, ") { issue -> issue.title },
        countOpenPrs = openedPrs.size,
        countClosedPrs = this.pullRequests.size - openedPrs.size,
        openedPrsTitles = openedPrs.joinToString("\n, ") { pr -> pr.title },
    )
}
