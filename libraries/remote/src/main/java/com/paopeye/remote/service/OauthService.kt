package com.paopeye.remote.service

import com.paopeye.remote.base.constant.NetworkConfigs
import com.paopeye.remote.model.responses.ResponseToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface OauthService {
//    @Headers("${NetworkConfigs.CUSTOM_HEADERS}: ${NetworkConfigs.OAUTH}")
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getOauthToken(
        @Field("grant_type") requestBody: String
    ): ResponseToken
}
