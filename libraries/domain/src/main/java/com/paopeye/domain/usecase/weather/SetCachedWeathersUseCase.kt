package com.paopeye.domain.usecase.weather

import com.paopeye.domain.model.Weathers
import com.paopeye.domain.repository.WeatherRepository

class SetCachedWeathersUseCase(private val repository: WeatherRepository) {
    fun invoke(request: Weathers) = repository.setWeathers(request)
}
