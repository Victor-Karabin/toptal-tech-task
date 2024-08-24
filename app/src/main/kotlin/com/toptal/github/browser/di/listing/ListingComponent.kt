package com.toptal.github.browser.di.listing

import com.toptal.data.di.NetworkingComponent
import dagger.Component

@Component(dependencies = [NetworkingComponent::class])
@ListingScope
interface ListingComponent : ListingProvider
