plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "ru.deltadelete.lab5"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.deltadelete.lab5"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    jvmToolchain(11)
}

val kotlin_version: String by rootProject.extra

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("com.facebook.fresco:fresco:3.1.0")
    // Kotlin
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    // Kotlin end
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}