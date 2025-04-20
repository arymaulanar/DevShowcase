package com.paopeye.remote.service

import com.paopeye.data.entity.UserEntity
import com.paopeye.remote.model.BaseResponse
import com.paopeye.remote.model.requests.RequestUser
import com.paopeye.remote.model.responses.ResponseUser
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("user/get-user")
    suspend fun getUser(@Body request: RequestUser): BaseResponse<ResponseUser, UserEntity>
}
