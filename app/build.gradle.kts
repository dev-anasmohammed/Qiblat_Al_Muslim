import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.anasmohammed.qiblatalmuslim"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anasmohammed.qiblatalmuslim"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }

        buildConfigField("String", "MAP_API_KEY", "\"${properties.getProperty("MAP_API_KEY")}\"")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.compiler)

    //lottie
    implementation(libs.lottie)

    //intuit-sdp
    implementation(libs.intuit)
    implementation(libs.google.service.location)

    //glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    //room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //motionToast
    implementation(libs.motionToast)

    //permissionX
    implementation(libs.permissionX)

    //bundles
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.retorfit)
    implementation(libs.bundles.googleMaps)
    implementation(libs.bundles.datastore)
}