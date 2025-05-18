package com.paopeye.data.di

import com.paopeye.data.repository.NewsRepositoryImpl
import com.paopeye.data.repository.PhotonRepositoryImpl
import com.paopeye.data.repository.UserRepositoryImpl
import com.paopeye.data.repository.WeatherRepositoryImpl
import com.paopeye.domain.repository.NewsRepository
import com.paopeye.domain.repository.PhotonRepository
import com.paopeye.domain.repository.UserRepository
import com.paopeye.domain.repository.WeatherRepository
import org.koin.dsl.module

val dataModule = module {
//    factory<OauthRepository> { OauthRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    factory<PhotonRepository> { PhotonRepositoryImpl(get()) }
}
