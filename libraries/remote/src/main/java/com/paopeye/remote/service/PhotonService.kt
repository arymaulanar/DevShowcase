package com.paopeye.remote.service

import com.paopeye.remote.model.responses.ResponseCityAutoCompletes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PhotonService {
    @GET("api")
    suspend fun getCityAutoComplete(
        @QueryMap request: Map<String, String>
    ): Response<ResponseCityAutoCompletes>
}
