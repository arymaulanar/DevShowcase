package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.WeatherRemoteDataSource
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.entity.WeathersEntity
import com.paopeye.remote.base.util.mapResponseCode
import com.paopeye.remote.model.requests.RequestWeather
import com.paopeye.remote.service.WeatherService

class WeatherRemoteDataSourceImpl(
    private val service: WeatherService
) : WeatherRemoteDataSource {
    override suspend fun getWeatherForecasts(request: WeatherEntity): ResponseEntity<WeathersEntity> {
        val response = service.getWeatherForecasts(
            RequestWeather.fromEntity(request).toQuery()
        )
        val weathersEntity = WeathersEntity(
            weathers = response.body()?.map { it.toEntity() }
        )
        return ResponseEntity(
            responseCode = mapResponseCode(response.code()),
            data = weathersEntity
        )
    }
}
