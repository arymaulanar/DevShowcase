package com.paopeye.kit.constant

object SecureKeys {
    init {
        System.loadLibrary("secure-keys") // Matches CMake target name
    }
    external fun getNewsApiKey(): String
    external fun getNewsKey(): String
    external fun getWeatherApiKey(): String
}
