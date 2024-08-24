package com.toptal.data.networking

interface Api {

    suspend fun getRepositoryDetails(request: ApiRepositoryRequest): ApiRepositoryDetails

    suspend fun getRepositories(request: ApiRepositoriesRequest): List<ApiRepository>
}

data class ApiRepositoryDetails(
    val name: String,
    val issues: List<Issue>,
    val pullRequests: List<PullRequest>,
)

data class Issue(
    val title: String,
    val isOpen: Boolean,
)

data class PullRequest(
    val title: String,
    val isOpen: Boolean,
)

data class ApiRepositoryRequest(
    val repoId: String,
    val issuesLimit: Int,
    val prsLimit: Int,
)

data class ApiRepositoriesRequest(
    val owner: String,
    val limit: Int,
)

data class ApiRepository(
    val id: String,
    val name: String,
    val url: String,
)
