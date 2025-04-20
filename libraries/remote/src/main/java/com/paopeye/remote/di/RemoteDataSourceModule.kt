package com.paopeye.remote.di

import com.paopeye.data.datasource.remote.OauthRemoteDataSource
import com.paopeye.data.datasource.remote.UserRemoteDataSource
import com.paopeye.remote.source.OauthRemoteDataSourceImpl
import com.paopeye.remote.source.UserRemoteDataSourceImpl
import org.koin.dsl.module

internal val remoteDataSourceModule = module {
    factory<OauthRemoteDataSource> { OauthRemoteDataSourceImpl(get()) }
    factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
}
