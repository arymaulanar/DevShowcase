plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
}

android {
    namespace = "com.paopeye.cache"
    compileSdk = libs.versions.compileSdk.get().toInt()
    flavorDimensions += libs.versions.flavorDimensions.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }

    productFlavors {
        create("dev") {
            dimension = "default"
            buildConfigField("Long", "NETWORK_TIMEOUT", "90L")
            buildConfigField("String", "ENVIRONMENT", "\"dev\"")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://private-46cd3-arymaulanar.apiary-mock.com/\""
            )
        }
        create("prod") {
            dimension = "default"
            buildConfigField("Long", "NETWORK_TIMEOUT", "60L")
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://private-46cd3-arymaulanar.apiary-mock.com/\""
            )
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(project(":libraries:data"))
    implementation(project(":libraries:kit"))
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
