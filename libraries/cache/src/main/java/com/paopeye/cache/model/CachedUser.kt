package com.paopeye.cache.model

import com.paopeye.data.entity.UserEntity
import com.paopeye.data.mapper.FromEntityMapper
import com.paopeye.data.mapper.ToEntityMapper

data class CachedUser(
    val userId: String? = null,
    val fullName: String? = null,
) : ToEntityMapper<UserEntity> {
    override fun toEntity() = UserEntity(
        userId = userId,
        fullName = fullName
    )

    companion object : FromEntityMapper<UserEntity, CachedUser> {
        override fun fromEntity(entity: UserEntity?) = CachedUser(
            userId = entity?.userId,
            fullName = entity?.fullName
        )
    }
}
