package com.toptal.plugins.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

internal val Project.libs
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal fun Project.getVersionCatalogVersion(name: String) =
    libs.findVersion(name).get().requiredVersion.toInt()
