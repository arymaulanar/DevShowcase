package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Weather
import com.paopeye.domain.model.Weathers

interface WeatherRepository {
    suspend fun getWeatherForecasts(request: Weather): DataState<Weathers>
    fun getWeathers(): Weathers
    fun setWeathers(request: Weathers)
}
