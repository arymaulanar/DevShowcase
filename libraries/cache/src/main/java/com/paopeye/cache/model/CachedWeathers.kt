package com.paopeye.cache.model

import com.paopeye.data.entity.WeathersEntity
import com.paopeye.data.mapper.FromEntityMapper
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty

data class CachedWeathers(
    val weathers: List<CachedWeather>? = null
) : ToEntityMapper<WeathersEntity> {
    override fun toEntity() = WeathersEntity(
        weathers = weathers?.map { it.toEntity() }.orEmpty()
    )

    companion object : FromEntityMapper<WeathersEntity, CachedWeathers> {
        override fun fromEntity(entity: WeathersEntity?) = CachedWeathers(
            weathers = entity?.weathers?.map { CachedWeather.fromEntity(it) }
        )
    }
}
