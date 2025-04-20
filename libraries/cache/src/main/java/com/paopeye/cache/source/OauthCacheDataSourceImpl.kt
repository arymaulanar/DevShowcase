package com.paopeye.cache.source

import com.paopeye.cache.memory.AppCache
import com.paopeye.cache.memory.Keys
import com.paopeye.cache.model.CachedToken
import com.paopeye.data.datasource.cache.OauthCacheDataSource
import com.paopeye.data.entity.TokenEntity
import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.extension.silence

class OauthCacheDataSourceImpl(
    private val appCache: AppCache
) : OauthCacheDataSource {
    override fun getToken(): TokenEntity {
        val cachedToken = appCache[Keys.TOKEN] as? CachedToken ?: CachedToken(
            accessToken = emptyString() //defaultToken
        )
        return cachedToken.toEntity()
    }

    override fun setToken(entity: TokenEntity) {
        silence {
            appCache[Keys.TOKEN] = CachedToken.fromEntity(entity)
        }
    }

    override fun clearToken() {
        appCache.remove(Keys.TOKEN)
    }
}
