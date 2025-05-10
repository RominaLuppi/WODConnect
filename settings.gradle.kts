
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.google.dagger.hilt.android") version "2.50"
        id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "WODConnect"
include(":app")
