package com.paopeye.data.datasource.cache

import com.paopeye.data.entity.TokenEntity

interface OauthCacheDataSource {
    fun getToken(): TokenEntity
    fun setToken(entity: TokenEntity)
    fun clearToken()
}
