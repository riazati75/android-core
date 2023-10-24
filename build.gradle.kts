buildscript {

    repositories {
        google()
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
    }
}

plugins {
    // android
    id("com.android.application")        version "8.3.0-alpha01" apply false
    id("com.android.library")            version "8.3.0-alpha01" apply false
    // jetbrains
    id("org.jetbrains.kotlin.android")   version "1.8.21"        apply false
}

group   = "ir.farsroidx"
version = "1.3.16"