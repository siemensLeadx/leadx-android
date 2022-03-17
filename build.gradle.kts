// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
        classpath("com.google.gms:google-services:4.3.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://jcenter.bintray.com/")
        maven(
            "https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1")
        maven {
            url =
                uri("https://identitydivision.pkgs.visualstudio.com/_packaging/AndroidADAL/maven/v1")
            name = "vsts-maven-adal-android"
            credentials {
                username =
                    "${
                        System.getenv("ENV_VSTS_MVN_ANDROIDADAL_USERNAME") != null ?: System.getenv(
                            "ENV_VSTS_MVN_ANDROIDADAL_USERNAME") ?: project.findProperty(
                            "vstsUsername")
                    }"
                password =
                    "${
                        System.getenv("ENV_VSTS_MVN_ANDROIDADAL_ACCESSTOKEN") != null ?: System.getenv(
                            "ENV_VSTS_MVN_ANDROIDADAL_ACCESSTOKEN") ?: project.findProperty("vstsMavenAccessToken")
                    }"
            }
        }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}