import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // android
    id("com.android.application")        version "8.2.0-alpha04" apply false
    id("com.android.library")            version "8.2.0-alpha04" apply false
    // jetbrains
    id("org.jetbrains.kotlin.android")   version "1.8.21"        apply false
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_1_9)
        languageVersion.set(KotlinVersion.KOTLIN_1_9)
    }
}