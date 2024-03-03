buildscript {

    repositories {
        google()
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
}

plugins {
    // android
    id("com.android.application")      version "8.2.2" apply false
    id("com.android.library")          version "8.2.2" apply false
    // jetbrains
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

group   = "ir.farsroidx"
version = "1.3.22"