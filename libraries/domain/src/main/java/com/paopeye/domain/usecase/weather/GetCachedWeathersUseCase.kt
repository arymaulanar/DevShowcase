package com.paopeye.domain.usecase.weather

import com.paopeye.domain.repository.WeatherRepository

class GetCachedWeathersUseCase(private val repository: WeatherRepository) {
    fun invoke() = repository.getWeathers()
}
