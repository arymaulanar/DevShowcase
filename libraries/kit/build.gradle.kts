import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
}

android {
    namespace = "com.paopeye.kit"
    compileSdk = libs.versions.compileSdk.get().toInt()
    flavorDimensions += libs.versions.flavorDimensions.get()

    defaultConfig {
        val newsApiKey = gradleLocalProperties(rootDir, providers).getProperty("NEWS_API_KEY") ?: ""
        val weatherApiKey =
            gradleLocalProperties(rootDir, providers).getProperty("WEATHER_API_KEY") ?: ""
        val keys = mapOf(
            "WEATHER_API_KEY" to weatherApiKey,
            "NEWS_API_KEY" to newsApiKey
        )
        externalNativeBuild {
            cmake {
                cppFlags += ""
                keys.forEach { (key, value) ->
                    arguments("-D${key}=${value}")
                }
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    ndkVersion = "25.1.8937393"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.security.crypto)
    implementation(libs.material)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.retrofit.gson.converter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
