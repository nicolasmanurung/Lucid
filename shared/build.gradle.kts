plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kmpNativeCoroutines)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.realmMongoDb)
    alias(libs.plugins.ksp)

}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.realm.library.base)
            implementation(libs.realm.library.sync)
            implementation(libs.koin.core)
            api(libs.koin.core)
            implementation(libs.koin.test)
        }
        androidMain.dependencies {
            compileOnly(libs.realm.library.base)
            compileOnly(libs.realm.library.sync)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
    }
}

android {
    namespace = "id.anaktoba.lucid.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    dependencies {
        testImplementation(libs.junit)
    }
}