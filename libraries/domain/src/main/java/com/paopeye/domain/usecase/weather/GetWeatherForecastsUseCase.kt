package com.paopeye.domain.usecase.weather

import com.paopeye.domain.model.Weather
import com.paopeye.domain.repository.WeatherRepository

class GetWeatherForecastsUseCase(private val repository: WeatherRepository) {
    suspend fun invoke(request: Weather) = repository.getWeatherForecasts(request)
}
