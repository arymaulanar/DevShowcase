package com.paopeye.remote.service

import com.paopeye.remote.base.constant.NetworkConfigs
import com.paopeye.remote.model.responses.ResponseArticles
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {
    @Headers("${NetworkConfigs.CUSTOM_HEADERS}: ${NetworkConfigs.NEWS_CONFIG}")
    @GET("v1/articles")
    suspend fun getArticles(): ResponseArticles
}
