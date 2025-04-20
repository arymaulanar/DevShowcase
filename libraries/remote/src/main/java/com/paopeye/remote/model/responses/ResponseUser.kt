package com.paopeye.remote.model.responses

import com.google.gson.annotations.SerializedName
import com.paopeye.data.entity.UserEntity
import com.paopeye.data.mapper.ToEntityMapper

data class ResponseUser(
    val userId: String? = null,
    @SerializedName("user_fullName")
    val fullName: String? = null
) : ToEntityMapper<UserEntity> {
    override fun toEntity() = UserEntity(
        userId = userId,
        fullName = fullName
    )
}
