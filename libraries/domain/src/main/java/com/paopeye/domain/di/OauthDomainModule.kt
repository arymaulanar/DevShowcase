package com.paopeye.domain.di

import com.paopeye.domain.usecase.oauth.GetOauthTokenUseCase
import org.koin.dsl.module

internal val oauthDomainModule = module {
    factory { GetOauthTokenUseCase(get()) }
}
