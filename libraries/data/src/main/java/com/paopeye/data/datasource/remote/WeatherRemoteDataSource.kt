package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.entity.WeathersEntity

interface WeatherRemoteDataSource {
    suspend fun getWeatherForecasts(request: WeatherEntity): ResponseEntity<WeathersEntity>
}
