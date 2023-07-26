plugins {
    // android
    id("com.android.library")
    // jetbrains
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    // maven
    id("maven-publish")
}

android {
    namespace  = "ir.farsroidx.core"
    compileSdk = 33
    defaultConfig {
        minSdk                    = 21
        multiDexEnabled           = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    // Android-X
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.6.0")
    implementation("androidx.exifinterface:exifinterface:1.3.6")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Material
    implementation("com.google.android.material:material:1.9.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Other
    api("com.airbnb.android:lottie:6.1.0")
    api("com.github.bumptech.glide:glide:4.15.1")
    api("com.github.samanzamani:PersianDate:1.6.1")
    api("com.github.PhilJay:MPAndroidChart:v3.1.0")
    api("com.github.abdularis:CircularImageView:1.5")
    api("com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")

    // Riazati75
    api("com.github.riazati75:ExpansionPanel:1.0.0")
    api("com.github.riazati75:PleaseAnimation:1.0.0")

    // =-------------------------------------------------------------------------------------------=
    // =--= Test Dependencies =---------------------------------------------= Test Dependencies =--=
    // =-------------------------------------------------------------------------------------------=

    // Unit Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {

    publications {

        register<MavenPublication>("release") {

            groupId    = "ir.farsroidx"
            artifactId = "android-core"
            version    = "1.1.0"

            afterEvaluate {
                from(
                    components["release"]
                )
            }
        }
    }
}