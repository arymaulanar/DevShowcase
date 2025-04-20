package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.cache.OauthCacheDataSource
import com.paopeye.data.datasource.remote.OauthRemoteDataSource
import com.paopeye.data.entity.TokenEntity
import com.paopeye.domain.model.Token
import com.paopeye.domain.repository.OauthRepository

class OauthRepositoryImpl(
    private val oauthRemoteDataSource: OauthRemoteDataSource,
    private val oauthCacheDataSource: OauthCacheDataSource
) : OauthRepository {
    override suspend fun getOauthToken() = DataStateBoundResource.createNetworkCall(
        isRetryUnauthorizedError = false,
        isRetryServerError = true,
        { tokenEntity -> tokenEntity?.let { oauthCacheDataSource.setToken(it) } },
        { oauthRemoteDataSource.getOauthToken() }
    ).getResult()

    override fun getCachedToken() = oauthCacheDataSource.getToken().toModel()

    override fun setToken(token: Token) {
        oauthCacheDataSource.setToken(TokenEntity.fromModel(token))
    }

    override fun clearCachedToken() = oauthCacheDataSource.clearToken()
}
