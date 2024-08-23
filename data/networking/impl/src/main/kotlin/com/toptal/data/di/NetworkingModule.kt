package com.toptal.data.di

import com.toptal.data.networking.Api
import dagger.Binds
import dagger.Module

@Module(includes = [ConnectionModule::class])
internal abstract class NetworkingModule {

    @Binds
    abstract fun api(impl: ApolloGraphQlApi): Api
}
