package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Token

interface OauthRepository {
    suspend fun getOauthToken(): DataState<Token>
    fun getCachedToken(): Token
    fun setToken(token: Token)
    fun clearCachedToken()
}
