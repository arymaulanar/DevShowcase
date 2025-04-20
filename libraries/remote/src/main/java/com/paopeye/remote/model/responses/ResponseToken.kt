package com.paopeye.remote.model.responses

import com.google.gson.annotations.SerializedName
import com.paopeye.data.entity.TokenEntity
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty

data class ResponseToken(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    @SerializedName("expires_in")
    val lifetime: Double? = null,
    val scope: String? = null
) : ToEntityMapper<TokenEntity> {
    override fun toEntity() = TokenEntity(
        accessToken = accessToken,
        tokenType = tokenType,
        lifetime = lifetime,
        expiredAt = System.currentTimeMillis() + (lifetime?.toLong().orEmpty() * IN_MILLIS),
        scope = scope
    )

    companion object {
        private const val IN_MILLIS = 1000L
    }
}
