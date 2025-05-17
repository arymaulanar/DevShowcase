package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.cache.WeathersCacheDataSource
import com.paopeye.data.datasource.remote.WeatherRemoteDataSource
import com.paopeye.data.entity.WeatherEntity
import com.paopeye.data.entity.WeathersEntity
import com.paopeye.domain.model.Weather
import com.paopeye.domain.model.Weathers
import com.paopeye.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weathersCacheDataSource: WeathersCacheDataSource
) : WeatherRepository {
    override suspend fun getWeatherForecasts(request: Weather) =
        DataStateBoundResource.createNetworkCall {
            weatherRemoteDataSource.getWeatherForecasts(
                WeatherEntity.fromModel(request)
            )
        }.getResult()

    override fun getWeathers(): Weathers {
        return weathersCacheDataSource.getWeathers().toModel()
    }

    override fun setWeathers(request: Weathers) {
        weathersCacheDataSource.setWeathers(WeathersEntity.fromModel(request))
    }
}
