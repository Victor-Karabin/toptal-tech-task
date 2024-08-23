package com.toptal.plugins

import com.toptal.plugins.internal.configureToolchainsAndJavaTarget
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")

        configureToolchainsAndJavaTarget()
    }
}
