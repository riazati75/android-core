plugins {
    // android
    id("com.android.application")
    // jetbrains
    id("org.jetbrains.kotlin.android")
}

android {
    namespace  = "ir.farsroidx.app"
    compileSdk = 33
    defaultConfig {
        applicationId = "ir.farsroidx.app"
        minSdk        = 21
        targetSdk     = 33
        versionCode   = 1
        versionName   = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Module
    implementation( project( mapOf("path" to ":core") ) )

    // Android-X
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // =-------------------------------------------------------------------------------------------=
    // =--= Test Dependencies =---------------------------------------------= Test Dependencies =--=
    // =-------------------------------------------------------------------------------------------=

    // Unit Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}