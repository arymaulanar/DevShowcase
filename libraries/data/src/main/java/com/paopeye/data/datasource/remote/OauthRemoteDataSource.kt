package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.TokenEntity

interface OauthRemoteDataSource {
    suspend fun getOauthToken(): ResponseEntity<TokenEntity>
}
