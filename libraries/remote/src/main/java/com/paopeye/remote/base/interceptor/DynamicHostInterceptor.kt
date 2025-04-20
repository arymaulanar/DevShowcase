package com.paopeye.remote.base.interceptor

import com.paopeye.kit.extension.emptyString
import okhttp3.Interceptor
import okhttp3.Response

internal class DynamicHostInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val originalResponse = chain.proceed(originRequest)
        val host = getHost(
            isRedirect = originalResponse.isRedirect,
            originalUrl = originRequest.url.host,
            redirectedUrl = originalResponse.header(LOCATION).orEmpty()
        )
        val requestWithUserAgent = originRequest.newBuilder()
            .header(HOST_KEY, host)
            .header(ACCEPT_KEY, ACCEPT_VALUE)
            .header(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE)
            .header(CONNECTION_KEY, CONNECTION_VALUE)
            .header(CACHE_CONTROL_KEY, CACHE_CONTROL_VALUE)
            .header(PRAGMA_KEY, PRAGMA_VALUE)
            .header(USER_AGENT_KEY, emptyString())
            .build()
        return originalResponse.newBuilder().request(requestWithUserAgent).build()
    }

    private fun getHost(isRedirect: Boolean, originalUrl: String, redirectedUrl: String): String {
        if (isRedirect && redirectedUrl.isNotEmpty()) return redirectedUrl
        return originalUrl
    }

    companion object {
        const val HOST_KEY = "Host"
        const val ACCEPT_KEY = "Accept"
        const val ACCEPT_ENCODING_KEY = "Accept-Encoding"
        const val CONNECTION_KEY = "Connection"
        const val CACHE_CONTROL_KEY = "Cache-Control"
        const val PRAGMA_KEY = "Pragma"
        const val USER_AGENT_KEY = "User-Agent"
        const val ACCEPT_VALUE = "*/*"
        const val ACCEPT_ENCODING_VALUE = "gzip, deflate, br"
        const val CONNECTION_VALUE = "keep-alive"
        const val CACHE_CONTROL_VALUE = "no-cache"
        const val PRAGMA_VALUE = "no-cache"
        const val LOCATION = "Location"
    }
}
