package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.User

data class UserEntity(
    val userId: String? = null,
    val fullName: String? = null,
) : ToModelMapper<User> {
    override fun toModel() = User(
        userId = userId.orEmpty(),
        fullName = fullName.orEmpty()
    )

    companion object : FromModelMapper<User, UserEntity> {
        override fun fromModel(model: User) = UserEntity(
            userId = model.userId,
            fullName = model.fullName
        )
    }
}
