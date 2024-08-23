package com.toptal.plugins

import com.android.build.gradle.LibraryExtension
import com.toptal.plugins.internal.configureToolchainsAndJavaTarget
import com.toptal.plugins.internal.getVersionCatalogVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")

        extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = getVersionCatalogVersion("build-android-compile-sdk")
            defaultConfig.minSdk = getVersionCatalogVersion("build-android-min-sdk")

            testOptions.targetSdk = getVersionCatalogVersion("build-android-target-sdk")
        }

        configureToolchainsAndJavaTarget()
    }
}
