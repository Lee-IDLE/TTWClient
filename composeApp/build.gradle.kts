import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // sqlDelight
            implementation(libs.sqldelight.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.coroutines.core)

            // 통신
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.io.ktor.ktor.client.content.negotiation)
            implementation(libs.io.ktor.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.ktor.client.cio)
            implementation(libs.ktor.client.websockets)
            implementation(libs.slf4j.simple)

            // sqlDelight
            implementation(libs.sqldelight.coroutines)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            // sqlDelight
            implementation(libs.sqldelight.jvm)
            implementation(libs.sqlite.jdbc)
        }
    }
    sqldelight {
        databases {
            create("TTWClientDB") {
                packageName = "org.lee.talk_to_we_client"
                verifyMigrations = false
            }
        }
    }
}

android {
    namespace = "org.lee.talk_to_we_client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.lee.talk_to_we_client"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    /*
    kotlinOptions {
        jvmTarget = "22"
    }
    */
}

dependencies {
    implementation(libs.androidx.foundation.layout.android)
    implementation(compose.materialIconsExtended)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    implementation(libs.ktor.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.ktor.client.logging)
    implementation(libs.ktor.client.auth)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.material3)
    implementation(libs.ktor.ktor.serialization.kotlinx.json)
    implementation(libs.core.ktx)

    debugImplementation(compose.uiTooling)


}

compose.desktop {
    application {
        mainClass = "org.lee.talk_to_we_client.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.lee.talk_to_we_client"
            packageVersion = "1.0.0"
        }
    }
}

tasks.withType<JavaCompile>{
    options.encoding = "UTF-8"
}

/*
- AGP 관련 오류 나는 경우
libs.versions.toml에 가서 agp의 버전을 내 Android Studio에 맞춰준다.
File -> Project Structure -> Gradle 버전 맞추기
 */