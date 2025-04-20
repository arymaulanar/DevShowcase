package com.paopeye.remote.model.requests

import com.paopeye.data.entity.UserEntity
import com.paopeye.data.mapper.FromEntityMapper

data class RequestUser(
    val userId: String? = null,
) {
    companion object : FromEntityMapper<UserEntity, RequestUser> {
        override fun fromEntity(entity: UserEntity?) = RequestUser(
            userId = entity?.userId
        )
    }
}
