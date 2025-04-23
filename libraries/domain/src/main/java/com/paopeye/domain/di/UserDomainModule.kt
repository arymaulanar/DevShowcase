package com.paopeye.domain.di

import com.paopeye.domain.usecase.news.GetArticlesUseCase
import com.paopeye.domain.usecase.user.GetUserUseCase
import org.koin.dsl.module

internal val userDomainModule = module {
    factory { GetUserUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
}
