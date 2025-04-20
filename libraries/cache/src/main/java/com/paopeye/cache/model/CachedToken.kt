package com.paopeye.cache.model

import com.paopeye.data.entity.TokenEntity
import com.paopeye.data.mapper.FromEntityMapper
import com.paopeye.data.mapper.ToEntityMapper

data class CachedToken(
    val accessToken: String? = null,
    val tokenType: String? = null,
    val lifetime: Double? = null,
    val expiredAt: Long? = null,
    val scope: String? = null
) : ToEntityMapper<TokenEntity> {
    override fun toEntity() = TokenEntity(
        accessToken = accessToken,
        tokenType = tokenType,
        lifetime = lifetime,
        expiredAt = expiredAt,
        scope = scope
    )

    companion object : FromEntityMapper<TokenEntity, CachedToken> {
        override fun fromEntity(entity: TokenEntity?) = CachedToken(
            accessToken = entity?.accessToken,
            tokenType = entity?.tokenType,
            lifetime = entity?.lifetime,
            expiredAt = entity?.expiredAt,
            scope = entity?.scope
        )
    }
}
