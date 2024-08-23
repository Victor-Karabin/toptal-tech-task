package com.toptal.github.browser

import com.toptal.data.di.DaggerNetworkingComponent
import com.toptal.data.di.GithubConfig
import com.toptal.github.browser.di.DaggerMainComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

internal class MainApplication : DaggerApplication() {

    private fun networkingComponent() = DaggerNetworkingComponent.factory().create(
        config = GithubConfig(
            token = BuildConfig.GITHUB_TOKEN,
            url = BuildConfig.GITHUB_API,
        ),
    )

    override fun applicationInjector(): AndroidInjector<MainApplication> = DaggerMainComponent.factory().create(
        application = this,
        networkingComponent = networkingComponent(),
    )
}
