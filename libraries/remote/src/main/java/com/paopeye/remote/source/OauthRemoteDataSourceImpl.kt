package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.OauthRemoteDataSource
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.TokenEntity
import com.paopeye.remote.service.OauthService

class OauthRemoteDataSourceImpl(
    private val oauthService: OauthService
) : OauthRemoteDataSource {
    override suspend fun getOauthToken(): ResponseEntity<TokenEntity> {
        return ResponseEntity(
            responseCode = ResponseEntity.SUCCESS_RESPONSE_CODE,
            data = oauthService.getOauthToken("client_credentials").toEntity()
        )
    }
}
