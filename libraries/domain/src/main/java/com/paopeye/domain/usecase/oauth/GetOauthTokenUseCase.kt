package com.paopeye.domain.usecase.oauth

import com.paopeye.domain.repository.OauthRepository

class GetOauthTokenUseCase(private val repository: OauthRepository) {
    suspend fun invoke() = repository.getOauthToken()

}
