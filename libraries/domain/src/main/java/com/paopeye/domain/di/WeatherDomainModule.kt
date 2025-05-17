package com.paopeye.domain.di

import com.paopeye.domain.usecase.weather.GetCachedWeathersUseCase
import com.paopeye.domain.usecase.weather.GetWeatherForecastsUseCase
import com.paopeye.domain.usecase.weather.SetCachedWeathersUseCase
import org.koin.dsl.module

internal val weatherDomainModule = module {
    factory { GetWeatherForecastsUseCase(get()) }
    factory { GetCachedWeathersUseCase(get()) }
    factory { SetCachedWeathersUseCase(get()) }
}
