package com.toptal.github.browser.di.details

import com.toptal.data.di.NetworkingComponent
import dagger.Component

@Component(dependencies = [NetworkingComponent::class])
@DetailsScope
interface DetailsComponent : DetailsProvider
