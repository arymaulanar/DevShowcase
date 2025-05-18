package com.paopeye.domain.di

import com.paopeye.domain.usecase.photon.GetCityAutoCompletesUseCase
import org.koin.dsl.module

internal val photonDomainModule = module {
    factory { GetCityAutoCompletesUseCase(get()) }
}
