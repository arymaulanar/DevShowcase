package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.UserRemoteDataSource
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.UserEntity
import com.paopeye.remote.model.requests.RequestUser
import com.paopeye.remote.service.UserService

class UserRemoteDataSourceImpl(private val service: UserService) : UserRemoteDataSource {
    override suspend fun getUserData(request: UserEntity): ResponseEntity<UserEntity> {
        return service.getUser(
            RequestUser.fromEntity(request)
        ).toEntityWithData { it?.toEntity() }
    }
}
