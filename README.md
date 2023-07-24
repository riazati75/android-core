# Android Core

farsroidx utilities and functions for android apps

## Gradle

Step 1: Add jitpack maven in your root `build.gradle` or `settings.gradle.kts`:

in `build.gradle`:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' } <--------
    }
}
```
OR `settings.gradle.kts`:
```kotlin
pluginManagement {
    repositories {
        ...
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven(url = "https://jitpack.io") <--------
    }
}
```

Step 2: Add the dependency: [![](https://jitpack.io/v/riazati75/android-core.svg)](https://jitpack.io/#riazati75/android-core)

in `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.riazati75:android-core:🔝<LAST_RELEASE_VERSION>🔝'
}
```
OR `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.riazati75:android-core:🔝<LAST_RELEASE_VERSION>🔝")
}
```

Good Luck and Enjoy! 😉