package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.orEmpty
import java.math.BigDecimal

data class WeatherEntity(
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
) : ToModelMapper<Weather> {
    override fun toModel() = Weather(
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

    companion object : FromModelMapper<Weather, WeatherEntity> {
        override fun fromModel(model: Weather) = WeatherEntity(
            latitude = model.latitude,
            longitude = model.longitude,
            timestamp = model.timestamp,
            currentTemp = model.currentTemp,
            feelsLikeTemp = model.feelsLikeTemp,
            humidity = model.humidity,
            minTemp = model.minTemp,
            maxTemp = model.maxTemp,
            weather = model.weather,
            cloudPct = model.cloudPct,
            windSpeed = model.windSpeed,
            windDegrees = model.windDegrees,
            cityName = model.cityName
        )
    }
}
