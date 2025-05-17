package com.paopeye.cache.di

import com.paopeye.cache.memory.AppCache
import com.paopeye.cache.source.WeathersCacheDataSourceImpl
import com.paopeye.data.datasource.cache.WeathersCacheDataSource
import org.koin.dsl.module

val cacheModule = module {
    single { AppCache() }
//    factory<OauthCacheDataSource> { OauthCacheDataSourceImpl(get()) }
    factory<WeathersCacheDataSource> { WeathersCacheDataSourceImpl(get()) }
}
