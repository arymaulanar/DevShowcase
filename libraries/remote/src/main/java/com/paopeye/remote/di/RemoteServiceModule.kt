package com.paopeye.remote.di

import com.paopeye.remote.base.createService
import com.paopeye.remote.service.OauthService
import com.paopeye.remote.service.UserService
import org.koin.dsl.module

internal val remoteServiceModule = module {
    factory { createService<OauthService>(get()) }
//    factory { createService<OauthService>(get(named(NetworkNames.OAUTH))) }
    factory { createService<UserService>(get()) }
}
