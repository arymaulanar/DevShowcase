package com.paopeye.kit.constant

object SecureKeys {
    init {
        System.loadLibrary("secure-keys")
    }
    external fun getNewsApiKey(): String
    external fun getNewsKey(): String
    external fun getWeatherApiKey(): String
    external fun getWeatherKey(): String
}
