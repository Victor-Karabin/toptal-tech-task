package com.toptal.github.browser.di

import android.content.Context
import com.toptal.github.browser.MainApplication

internal fun Context.networkingComponent() = (applicationContext as MainApplication).networkingComponent
