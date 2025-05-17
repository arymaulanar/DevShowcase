package com.paopeye.remote.model.requests

import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.mapper.FromEntityMapper

data class RequestWeather(
    val lat: String? = null,
    val lon: String? = null
) {
    fun toQuery() = mapOf(
        "lat" to lat.orEmpty(),
        "lon" to lon.orEmpty(),
    )

    companion object : FromEntityMapper<WeatherEntity, RequestWeather> {
        override fun fromEntity(entity: WeatherEntity?) = RequestWeather(
            lat = entity?.latitude,
            lon = entity?.longitude
        )
    }
}
