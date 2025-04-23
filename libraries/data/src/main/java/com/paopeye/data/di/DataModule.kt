package com.paopeye.data.di

import com.paopeye.data.repository.NewsRepositoryImpl
import com.paopeye.data.repository.OauthRepositoryImpl
import com.paopeye.data.repository.UserRepositoryImpl
import com.paopeye.domain.repository.NewsRepository
import com.paopeye.domain.repository.OauthRepository
import com.paopeye.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
//    factory<OauthRepository> { OauthRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}
