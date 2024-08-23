package com.toptal.plugins.internal

import com.android.build.gradle.BaseExtension
import com.toptal.plugins.ApplicationPlugin
import com.toptal.plugins.KotlinPlugin
import com.toptal.plugins.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureToolchainsAndJavaTarget() {
    val javaTarget = getVersionCatalogVersion("build-java-target")
    val javaCompilation = getVersionCatalogVersion("build-java-compile")
    kotlinExtension.apply {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(javaCompilation))
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(javaTarget.toString()))
    }

    plugins.withType(KotlinPlugin::class.java) {
        tasks.withType<JavaCompile>().configureEach {
            options.release.set(javaTarget)
        }
    }

    listOf(
        LibraryPlugin::class,
        ApplicationPlugin::class,
    )
        .forEach {
            plugins.withType(it.java) {
                // Explanation: https://issuetracker.google.com/issues/278800528#comment5
                extensions.getByType<BaseExtension>().apply {
                    compileOptions {
                        sourceCompatibility = JavaVersion.toVersion(javaTarget)
                        targetCompatibility = JavaVersion.toVersion(javaTarget)
                    }
                }
            }
        }
}
