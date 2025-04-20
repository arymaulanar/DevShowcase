plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
}

android {
    namespace = "com.paopeye.resttemplate"
    compileSdk = libs.versions.compileSdk.get().toInt()
    flavorDimensions += libs.versions.flavorDimensions.get()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("dev") {
            applicationId = libs.versions.applicationIdDev.get()
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
            applicationId = libs.versions.applicationIdProduction.get()
            dimension = libs.versions.flavorDimensions.get()
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
        release {
            isShrinkResources = true
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

    implementation(project(":libraries:domain"))
    implementation(project(":libraries:cache"))
    implementation(project(":libraries:data"))
    implementation(project(":libraries:kit"))
    implementation(project(":libraries:remote"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.security.crypto)
    implementation(libs.material)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.coroutines)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.okhttp3.urlconnection)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
