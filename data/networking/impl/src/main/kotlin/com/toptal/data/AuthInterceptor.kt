package com.toptal.data

import com.toptal.data.di.GithubConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val config: GithubConfig,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${config.token}")
            .build()
            .let(chain::proceed)
    }
}
