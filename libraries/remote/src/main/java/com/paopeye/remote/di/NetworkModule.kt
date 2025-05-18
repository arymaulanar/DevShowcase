package com.paopeye.remote.di

import com.paopeye.remote.BuildConfig
import com.paopeye.remote.base.Network
import com.paopeye.remote.base.constant.NetworkNames
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    val networkTimeout = BuildConfig.NETWORK_TIMEOUT
    single {
        Network.getOkHttpClient(androidContext(), networkTimeout)
    }
    single { Network.getGson() }
    single(named(NetworkNames.NEWS)) {
        Network.getRetrofit(
            BuildConfig.BASE_NEWS_URL,
            get(),
            get()
        )
    }
    single(named(NetworkNames.WEATHER)) {
        Network.getRetrofit(
            BuildConfig.BASE_WEATHER_URL,
            get(),
            get()
        )
    }
    single(named(NetworkNames.PHOTON)) {
        Network.getRetrofit(
            BuildConfig.BASE_PHOTON_URL,
            get(),
            get()
        )
    }
}
