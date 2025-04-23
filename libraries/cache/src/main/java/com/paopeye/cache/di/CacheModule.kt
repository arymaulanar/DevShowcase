package com.paopeye.cache.di

import com.paopeye.cache.memory.AppCache
import com.paopeye.cache.source.OauthCacheDataSourceImpl
import com.paopeye.data.datasource.cache.OauthCacheDataSource
import org.koin.dsl.module

val cacheModule = module {
    single { AppCache() }
//    factory<OauthCacheDataSource> { OauthCacheDataSourceImpl(get()) }
}
