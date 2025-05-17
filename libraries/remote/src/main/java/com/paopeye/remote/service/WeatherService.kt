package com.paopeye.remote.service

import com.paopeye.remote.base.constant.NetworkConfigs
import com.paopeye.remote.model.responses.ResponseWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface WeatherService {
    @Headers("${NetworkConfigs.CUSTOM_HEADERS}: ${NetworkConfigs.WEATHER_CONFIG}")
    @GET("v1/weatherforecast")
    suspend fun getWeatherForecasts(
        @QueryMap request: Map<String, String>
    ): Response<List<ResponseWeather>>
}
