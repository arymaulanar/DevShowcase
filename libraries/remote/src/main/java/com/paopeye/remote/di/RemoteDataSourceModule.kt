package com.paopeye.remote.di

import com.paopeye.data.datasource.remote.NewsRemoteDataSource
import com.paopeye.data.datasource.remote.OauthRemoteDataSource
import com.paopeye.data.datasource.remote.PhotonRemoteDataSource
import com.paopeye.data.datasource.remote.UserRemoteDataSource
import com.paopeye.data.datasource.remote.WeatherRemoteDataSource
import com.paopeye.remote.source.NewsRemoteDataSourceImpl
import com.paopeye.remote.source.OauthRemoteDataSourceImpl
import com.paopeye.remote.source.PhotonRemoteDataSourceImpl
import com.paopeye.remote.source.UserRemoteDataSourceImpl
import com.paopeye.remote.source.WeatherRemoteDataSourceImpl
import org.koin.dsl.module

internal val remoteDataSourceModule = module {
    factory<OauthRemoteDataSource> { OauthRemoteDataSourceImpl(get()) }
    factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    factory<NewsRemoteDataSource> { NewsRemoteDataSourceImpl(get()) }
    factory<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }
    factory<PhotonRemoteDataSource> { PhotonRemoteDataSourceImpl(get()) }
}
