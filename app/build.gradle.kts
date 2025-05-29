plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias (libs.plugins.hilt.application)
    // Habilita el procesador de anotaciones en Kotlin
    id("com.google.devtools.ksp")
    id ("kotlin-android")
    id ("kotlin-kapt") // Necesario para la anotación @Inject
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)

}

android {
    namespace = "com.example.wodconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wodconnect"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true

    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.ui.v140)
    implementation (libs.androidx.material3.v100)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation (libs.androidx.runtime.livedata)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation (libs.androidx.material3.v121)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.foundation)

    implementation (libs.kotlin.stdlib)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    implementation (libs.hilt.navigation.compose)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}