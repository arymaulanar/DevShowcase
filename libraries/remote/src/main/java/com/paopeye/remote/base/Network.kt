package com.paopeye.remote.base

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paopeye.kit.util.Environment
import com.paopeye.remote.adapter.BigDecimalWithOutScientificTypeAdapter
import com.paopeye.remote.adapter.DoubleToBigDecimalTypeAdapter
import com.paopeye.remote.base.constant.NetworkConfigs
import com.paopeye.remote.base.interceptor.DynamicHostInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

object Network {
    fun getOkHttpClient(
        context: Context,
        isDynamicHost: Boolean,
        networkTimeout: Long = NetworkConfigs.TIMEOUT
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            hostnameVerifier(hostnameVerifier = { _, _ -> true })
            if (!Environment.IS_PRODUCTION) {
                addInterceptor(getHttpLoggingInterceptor())
                addInterceptor(getChuckInterceptor(context))
            }
            if (isDynamicHost) {
                addInterceptor(DynamicHostInterceptor())
                return@apply
            }
            readTimeout(networkTimeout, TimeUnit.SECONDS)
            connectTimeout(networkTimeout, TimeUnit.SECONDS)
        }.build()
    }

    fun getRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                object : TypeToken<Double>() {}.type,
                DoubleToBigDecimalTypeAdapter()
            )
            .registerTypeAdapter(
                object : TypeToken<BigDecimal>() {}.type,
                BigDecimalWithOutScientificTypeAdapter()
            )
            .create()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLogInterceptor
    }

    private fun getChuckInterceptor(context: Context): ChuckerInterceptor {
        val chuckCollector = ChuckerCollector(context = context, showNotification = true)
        return ChuckerInterceptor.Builder(context)
            .collector(chuckCollector)
            .alwaysReadResponseBody(true)
            .build()
    }
}
