package com.toptal.data.di

import com.apollographql.apollo.ApolloClient
import com.toptal.data.networking.Api
import com.toptal.data.networking.ApiRepositoryDetails
import com.toptal.data.networking.ApiRepositoryRequest
import com.toptal.graphql.RepositoryDetailsQuery
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

internal class ApolloGraphQlApi @Inject constructor(
    private val client: ApolloClient,
) : Api {

    override suspend fun getRepositoryDetails(request: ApiRepositoryRequest): ApiRepositoryDetails {
        val response = client.query(
            RepositoryDetailsQuery(
                owner = request.owner,
                name = request.name,
            ),
        )
            .toFlow()
            .toList()
            .last()

        val data = response.data?.repository
        return if (data == null) {
            throw IllegalStateException("Missing exception handling", response.exception)
        } else {
            ApiRepositoryDetails(
                id = data.id,
                name = data.name,
                url = data.url.toString(),
            )
        }
    }
}
