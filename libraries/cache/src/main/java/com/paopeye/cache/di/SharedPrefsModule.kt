package com.paopeye.cache.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.paopeye.cache.preferences.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val APP_SECURE_PREFERENCES: String = "encrypted_prefs"
val sharedPrefsModule = module {
    single {
        MasterKey.Builder(androidContext()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }
    single {
        EncryptedSharedPreferences.create(
            androidContext(),
            APP_SECURE_PREFERENCES,
            get(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single { AppPreferences(get()) }
}
