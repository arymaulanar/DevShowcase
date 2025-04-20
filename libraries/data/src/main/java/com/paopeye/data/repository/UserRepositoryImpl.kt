package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.remote.UserRemoteDataSource
import com.paopeye.data.entity.UserEntity
import com.paopeye.domain.model.User
import com.paopeye.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getUser(request: User) = DataStateBoundResource.createNetworkCall {
        userRemoteDataSource.getUserData(
            UserEntity.fromModel(request)
        )
    }.getResult()
}
