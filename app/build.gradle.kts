import com.android.aaptcompiler.parseScreenWidthDp
import com.android.tools.analytics.AnalyticsPaths

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.proyectomarzo.flashmeet"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.proyectomarzo.flashmeet"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation (libs.material.v140)
    implementation(libs.glide.v4132)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit para comunicación con API
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // OkHttp para interceptores y logging
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
}
