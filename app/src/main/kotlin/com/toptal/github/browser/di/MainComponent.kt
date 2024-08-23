package com.toptal.github.browser.di

import com.toptal.data.di.NetworkingComponent
import com.toptal.github.browser.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
    ],
    dependencies = [
        NetworkingComponent::class,
    ],
)
internal interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: MainApplication,
            networkingComponent: NetworkingComponent,
        ): MainComponent
    }
}
