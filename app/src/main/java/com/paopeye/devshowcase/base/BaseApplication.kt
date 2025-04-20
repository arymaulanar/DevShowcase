package com.paopeye.devshowcase.base

import android.app.Application
import com.paopeye.devshowcase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
