package com.toptal.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.toptal.plugins.internal.configureToolchainsAndJavaTarget
import com.toptal.plugins.internal.getVersionCatalogVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")

        extensions.getByType(ApplicationExtension::class.java).apply {
            compileSdk = getVersionCatalogVersion("build-android-compile-sdk")
            defaultConfig.minSdk = getVersionCatalogVersion("build-android-min-sdk")
            defaultConfig.targetSdk = getVersionCatalogVersion("build-android-target-sdk")
        }

        configureToolchainsAndJavaTarget()
    }
}
