package com.paopeye.remote.model.responses

import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty
import java.math.BigDecimal

data class ResponseWeather(
    val timestamp: Long? = null,
    val temp: Int? = null,
    val feelsLike: Int? = null,
    val humidity: Int? = null,
    val minTemp: Int? = null,
    val maxTemp: Int? = null,
    val weather: String? = null,
    val cloudPct: Int? = null,
    val windSpeed: BigDecimal? = null,
    val windDegrees: Int? = null
) : ToEntityMapper<WeatherEntity> {
    override fun toEntity() = WeatherEntity(
        timestamp = timestamp.orEmpty(),
        currentTemp = temp.orEmpty(),
        feelsLikeTemp = feelsLike.orEmpty(),
        humidity = humidity.orEmpty(),
        minTemp = minTemp.orEmpty(),
        maxTemp = maxTemp.orEmpty(),
        weather = weather.orEmpty(),
        cloudPct = cloudPct.orEmpty(),
        windSpeed = windSpeed.orEmpty(),
        windDegrees = windDegrees.orEmpty()
    )
}
