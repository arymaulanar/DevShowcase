package com.paopeye.remote.base.interceptor

import com.paopeye.kit.extension.getNewsApiKey
import com.paopeye.kit.extension.getNewsKey
import com.paopeye.remote.base.constant.NetworkConfigs
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()
        val customHeaderValue = request.headers.values(NetworkConfigs.CUSTOM_HEADERS)
        val isNewsUrl = NetworkConfigs.NEWS_CONFIG in customHeaderValue
        val isWeatherConfig = NetworkConfigs.WEATHER_CONFIG in customHeaderValue
        if (isNewsUrl) {
            newRequestBuilder.header(getNewsKey(), getNewsApiKey())
        }
        if (isWeatherConfig) {
            //TODO
        }
        return chain.proceed(newRequestBuilder.removeHeader(NetworkConfigs.CUSTOM_HEADERS).build())
    }
}
