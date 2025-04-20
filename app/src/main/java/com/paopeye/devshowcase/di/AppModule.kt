package com.paopeye.devshowcase.di

import com.paopeye.cache.di.cacheModule
import com.paopeye.cache.di.sharedPrefsModule
import com.paopeye.data.di.dataModule
import com.paopeye.domain.di.domainModule
import com.paopeye.kit.di.kitModule
import com.paopeye.remote.di.remoteModule

internal val cacheLibrary = cacheModule + sharedPrefsModule
internal val appModule = viewModelModule+ cacheLibrary + dataModule + domainModule + kitModule + remoteModule
