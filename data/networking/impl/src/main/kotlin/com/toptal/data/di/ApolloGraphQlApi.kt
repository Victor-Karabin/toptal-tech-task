package com.toptal.data.di

import com.apollographql.apollo.ApolloClient
import com.toptal.data.networking.Api
import com.toptal.data.networking.ApiRepositoriesRequest
import com.toptal.data.networking.ApiRepository
import com.toptal.data.networking.ApiRepositoryDetails
import com.toptal.data.networking.ApiRepositoryRequest
import com.toptal.data.networking.Issue
import com.toptal.data.networking.PullRequest
import com.toptal.graphql.GetUserRepositoriesQuery
import com.toptal.graphql.RepositoryDetailsQuery
import com.toptal.graphql.type.IssueState
import com.toptal.graphql.type.PullRequestState
import javax.inject.Inject

internal class ApolloGraphQlApi @Inject constructor(
    private val client: ApolloClient,
) : Api {

    override suspend fun getRepositoryDetails(request: ApiRepositoryRequest): ApiRepositoryDetails {
        val query = RepositoryDetailsQuery(request.repoId, request.issuesLimit, request.prsLimit)
        val response = client.query(query).execute()
        val data = response.data

        return if (response.hasErrors() || data == null) {
            throw IllegalStateException("Missing exception handling", response.exception)
        } else {
            ApiRepositoryDetails(
                name = data.node?.onRepository?.name ?: "",
                issues = data.node?.onRepository?.issues?.nodes?.map { node ->
                    Issue(
                        title = node?.title ?: "",
                        isOpen = node?.state == IssueState.OPEN,
                    )
                } ?: emptyList(),
                pullRequests = data.node?.onRepository?.pullRequests?.nodes?.map { node ->
                    PullRequest(
                        title = node?.title ?: "",
                        isOpen = node?.state == PullRequestState.OPEN,
                    )
                } ?: emptyList(),
            )
        }
    }

    override suspend fun getRepositories(request: ApiRepositoriesRequest): List<ApiRepository> {
        val query = GetUserRepositoriesQuery(username = request.owner, first = request.limit)
        val response = client.query(query).execute()
        val data = response.data

        return if (response.hasErrors() || data == null) {
            throw IllegalStateException("Missing exception handling", response.exception)
        } else {
            data.user?.repositories?.nodes?.filterNotNull()
                ?.map { node: GetUserRepositoriesQuery.Node ->
                    ApiRepository(
                        id = node.id,
                        name = node.name,
                        url = node.url.toString(),
                    )
                }
                ?: emptyList()
        }
    }
}
