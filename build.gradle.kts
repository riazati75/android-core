buildscript {
    val agp_version by extra("8.3.2")
}

plugins {
    // android
    id("com.android.application")        version "8.3.2"  apply false
    id("com.android.library")            version "8.3.2"  apply false
    // jetbrains
    id("org.jetbrains.kotlin.android")   version "1.9.23" apply false
    // androidx
    id("androidx.navigation.safeargs")   version "2.7.7"  apply false
}

group   = "ir.farsroidx"
version = "1.3.23"