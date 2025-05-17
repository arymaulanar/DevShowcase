package com.paopeye.cache.model

import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.mapper.FromEntityMapper
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty
import java.math.BigDecimal

data class CachedWeather(
    val latitude: String? = null,
    val longitude: String? = null,
    val timestamp: Long? = null,
    val currentTemp: Int? = null,
    val feelsLikeTemp: Int? = null,
    val humidity: Int? = null,
    val minTemp: Int? = null,
    val maxTemp: Int? = null,
    val weather: String? = null,
    val cloudPct: Int? = null,
    val windSpeed: BigDecimal? = null,
    val windDegrees: Int? = null,
    val cityName: String? = null
) : ToEntityMapper<WeatherEntity> {
    override fun toEntity() = WeatherEntity(
        latitude = latitude.orEmpty(),
        longitude = longitude.orEmpty(),
        timestamp = timestamp.orEmpty(),
        currentTemp = currentTemp.orEmpty(),
        feelsLikeTemp = feelsLikeTemp.orEmpty(),
        humidity = humidity.orEmpty(),
        minTemp = minTemp.orEmpty(),
        maxTemp = maxTemp.orEmpty(),
        weather = weather.orEmpty(),
        cloudPct = cloudPct.orEmpty(),
        windSpeed = windSpeed.orEmpty(),
        windDegrees = windDegrees.orEmpty(),
        cityName = cityName.orEmpty()
    )

    companion object : FromEntityMapper<WeatherEntity, CachedWeather> {
        override fun fromEntity(entity: WeatherEntity?) = CachedWeather(
            latitude = entity?.latitude,
            longitude = entity?.longitude,
            timestamp = entity?.timestamp,
            currentTemp = entity?.currentTemp,
            feelsLikeTemp = entity?.feelsLikeTemp,
            humidity = entity?.humidity,
            minTemp = entity?.minTemp,
            maxTemp = entity?.maxTemp,
            weather = entity?.weather,
            cloudPct = entity?.cloudPct,
            windSpeed = entity?.windSpeed,
            windDegrees = entity?.windDegrees,
            cityName = entity?.cityName
        )
    }
}
