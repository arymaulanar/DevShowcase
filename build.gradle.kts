plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

fun com.android.build.api.dsl.ProductFlavor.applyDevConfig() {
    dimension = "default"
    buildConfigField("Long", "NETWORK_TIMEOUT", "90L")
    buildConfigField("String", "ENVIRONMENT", "\"dev\"")
    buildConfigField(
        "String",
        "BASE_NEWS_URL",
        "\"https://private-a02d60-devshowcase.apiary-mock.com/\""
    )
    buildConfigField(
        "String",
        "BASE_WEATHER_URL",
        "\"https://private-a02d60-devshowcase.apiary-mock.com/\""
    )
}

fun com.android.build.api.dsl.ProductFlavor.applyProdConfig() {
    dimension = "default"
    buildConfigField("Long", "NETWORK_TIMEOUT", "60L")
    buildConfigField("String", "ENVIRONMENT", "\"prod\"")
    buildConfigField(
        "String",
        "BASE_NEWS_URL",
        "\"https://api.finlight.me/\""
    )
    buildConfigField(
        "String",
        "BASE_WEATHER_URL",
        "\"https://api.api-ninjas.com/\""
    )
}
subprojects {
    plugins.withId("com.android.application") {
        configure<com.android.build.gradle.BaseExtension> {
            productFlavors {
                create("dev") {
                    applicationId = libs.versions.applicationIdDev.get()
                    applyDevConfig()
                }
                create("prod") {
                    applicationId = libs.versions.applicationIdProduction.get()
                    applyProdConfig()
                }
            }
        }
    }
    plugins.withId("com.android.library") {
        configure<com.android.build.gradle.BaseExtension> {
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                multiDexEnabled = true
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            productFlavors {
                create("dev") { applyDevConfig() }
                create("prod") { applyProdConfig() }
            }
        }
    }
}
