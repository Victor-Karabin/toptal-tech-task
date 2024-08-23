package com.toptal.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.okHttpClient
import com.toptal.data.AuthInterceptor
import com.toptal.data.adapters.UriToStringAdapter
import com.toptal.graphql.type.URI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
internal class ConnectionModule {

    @IntoSet
    @Provides
    fun interceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Provides
    @Reusable
    fun apollo(
        config: GithubConfig,
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl(config.url)
        .okHttpClient(
            OkHttpClient.Builder().apply {
                interceptors.forEach(::addInterceptor)
            }.build(),
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = CACHE_SIZE))
        .addCustomScalarAdapter(URI.type, UriToStringAdapter)
        .build()

    companion object {
        private const val CACHE_SIZE = 1024 * 1024
    }
}
