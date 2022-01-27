plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("live-release") {
            storeFile = file("leadx-key.jks")
            storePassword = "leadx_key_2022"
            keyPassword = "leadx_key_2022"
            keyAlias = "leadx-key"
        }
    }
    compileSdk = 31
    defaultConfig {
        applicationId = "com.siemens.leadx"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    flavorDimensions += listOf(Config.DEFAULT)
    productFlavors {
        all {
            dimension = Config.DEFAULT
        }
        create("staging") {
            buildConfigField(
                Config.STRING_TYPE,
                Config.MAIN_HOST,
                "\"https://tarekelsayed-001-site1.htempurl.com/\""
            )
        }
        create("live") {
            buildConfigField(
                Config.STRING_TYPE,
                Config.MAIN_HOST,
                "\"https://dashboard.siemenshealthineers-leadx.com/\""
            )
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
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
    }
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        signingConfig = signingConfigs.getByName("live-release")
    }
    packagingOptions {
        resources.excludes.add("META-INF/jersey-module-version")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha03")
    implementation("com.scottyab:rootbeer-lib:0.1.0")

    // testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    // dagger
    implementation("com.google.dagger:hilt-android:2.39.1")
    kapt("com.google.dagger:hilt-android-compiler:2.39.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.0")

    // rxjava
    implementation("io.reactivex.rxjava2:rxjava:2.2.10")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.10.0")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    // viewmodel
    implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation("android.arch.lifecycle:viewmodel:1.1.1")

    // others
    implementation("com.github.tapadoo:alerter:7.2.4")
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // paging
    implementation("android.arch.paging:runtime:1.0.1")
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    // microsoft
    implementation("com.microsoft.identity.client:msal:2.2.1")
    implementation("com.microsoft.graph:microsoft-graph:1.5.0")

    // firebase
    implementation("com.google.firebase:firebase-analytics:19.0.2")
    implementation("com.google.firebase:firebase-messaging:22.0.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

private object Config {
    const val STRING_TYPE = "String"
    const val MAIN_HOST = "MAIN_HOST"
    const val DEFAULT = "default"
}
