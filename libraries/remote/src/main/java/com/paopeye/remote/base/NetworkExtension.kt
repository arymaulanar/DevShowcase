package com.paopeye.remote.base

import retrofit2.Retrofit

inline fun <reified SERVICE> createService(retrofit: Retrofit): SERVICE {
    return retrofit.create(SERVICE::class.java)
}
