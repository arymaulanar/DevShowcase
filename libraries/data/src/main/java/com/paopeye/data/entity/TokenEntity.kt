package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Token
import com.paopeye.kit.extension.orEmpty

data class TokenEntity(
    val accessToken: String? = null,
    val tokenType: String? = null,
    val lifetime: Double? = null,
    val expiredAt: Long? = null,
    val scope: String? = null
) : ToModelMapper<Token> {
    override fun toModel() = Token(
        accessToken = accessToken.orEmpty(),
        tokenType = tokenType.orEmpty(),
        lifetime = lifetime.orEmpty(),
        expiredAt = expiredAt.orEmpty(),
        scope = scope.orEmpty()
    )

    companion object : FromModelMapper<Token, TokenEntity> {
        override fun fromModel(model: Token) = TokenEntity(
            accessToken = model.accessToken,
            tokenType = model.tokenType,
            lifetime = model.lifetime,
            expiredAt = System.currentTimeMillis() + model.lifetime.toLong().orEmpty(),
            scope = model.scope
        )
    }
}
