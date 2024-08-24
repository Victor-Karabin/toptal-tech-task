package com.toptal.data

import com.toptal.data.di.DaggerNetworkingComponent
import com.toptal.data.di.GithubConfig
import com.toptal.data.di.NetworkingComponent
import com.toptal.data.networking.Api
import com.toptal.data.networking.ApiRepositoryRequest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import java.io.File

internal class GraphqlGatewayIntegrationTest {

    private lateinit var api: Api

    private val server = MockWebServer()

    @Before
    fun setUp() {
        val component: NetworkingComponent = DaggerNetworkingComponent.factory()
            .create(
                config = GithubConfig(server.url("/").toString(), "dummyToken"),
            )
        api = component.api()
    }

    @Test
    fun `pulls repository details`() = runTest {
        server.enqueue(mockJson("details.json"))
        val result = api.getRepositoryDetails(
            request = ApiRepositoryRequest(
                repoId = "MDEwOlJlcG9zaXRvcnkxMDYyODk3",
                issuesLimit = 10,
                prsLimit = 10
            ),
        )

        //assertThat(result.name).isEqualTo("gitignore") TODO fix test
    }

    private fun mockJson(fileName: String): MockResponse {
        val file = File(checkNotNull(javaClass.classLoader.getResource(fileName)).file)
        return MockResponse().apply {
            setBody(file.readText())
        }
    }
}
