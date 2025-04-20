package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.UserEntity

interface UserRemoteDataSource {
    suspend fun getUserData(
        request: UserEntity
    ): ResponseEntity<UserEntity>
}
