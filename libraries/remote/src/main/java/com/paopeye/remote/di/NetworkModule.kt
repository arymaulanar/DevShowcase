package com.paopeye.remote.di

import com.paopeye.remote.base.Network
import com.paopeye.remote.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val networkModule = module {
    val networkTimeout = BuildConfig.NETWORK_TIMEOUT
    single {
        Network.getOkHttpClient(androidContext(), false, networkTimeout)
    }
    single { Network.getGson() }
    single { Network.getRetrofit(BuildConfig.BASE_URL, get(), get()) }

//    single(named(NetworkNames.OAUTH)) {
//        Network.getRetrofit(BuildConfig.BASE_URL_OAUTH, get())
//    }
}
