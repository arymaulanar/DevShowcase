package com.paopeye.devshowcase.enums

import com.paopeye.devshowcase.R
import com.paopeye.kit.extension.emptyInt

enum class WeatherIconType {
    UNSPECIFIED {
        override fun getIcon() = emptyInt()
    },
    CLEAR {
        override fun getIcon() = R.drawable.ic_clear_day_24
    },
    CLOUDS {
        override fun getIcon() = R.drawable.ic_cloudy_24
    },
    RAIN {
        override fun getIcon() = R.drawable.ic_rain_24
    },
    SNOW {
        override fun getIcon() = R.drawable.ic_snow_24
    };

    abstract fun getIcon(): Int
    companion object {
        fun getType(weather: String): WeatherIconType {
            val currentWeather = weather.uppercase()
            if (currentWeather == CLEAR.name) return CLEAR
            if (currentWeather == CLOUDS.name) return CLOUDS
            if (currentWeather == RAIN.name) return RAIN
            if (currentWeather == SNOW.name) return SNOW
            return UNSPECIFIED
        }
    }
}
