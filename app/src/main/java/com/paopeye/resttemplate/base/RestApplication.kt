package com.paopeye.resttemplate.base

import android.app.Application
import com.paopeye.resttemplate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
