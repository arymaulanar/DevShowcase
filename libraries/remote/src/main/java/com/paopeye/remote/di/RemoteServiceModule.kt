package com.paopeye.remote.di

import com.paopeye.remote.base.constant.NetworkNames
import com.paopeye.remote.base.createService
import com.paopeye.remote.service.NewsService
import com.paopeye.remote.service.PhotonService
import com.paopeye.remote.service.UserService
import com.paopeye.remote.service.WeatherService
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val remoteServiceModule = module {
    factory { createService<UserService>(get()) }
    factory { createService<NewsService>(get(named(NetworkNames.NEWS))) }
    factory { createService<WeatherService>(get(named(NetworkNames.WEATHER))) }
    factory { createService<PhotonService>(get(named(NetworkNames.PHOTON))) }
}
