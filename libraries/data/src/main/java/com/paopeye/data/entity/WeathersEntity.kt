package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Weathers
import com.paopeye.kit.extension.orEmpty

data class WeathersEntity(
    val weathers: List<WeatherEntity>? = null
) : ToModelMapper<Weathers> {
    override fun toModel() = Weathers(
        weathers = weathers?.map { it.toModel() }.orEmpty()
    )

    companion object : FromModelMapper<Weathers, WeathersEntity> {
        override fun fromModel(model: Weathers) = WeathersEntity(
            weathers = model.weathers.map { WeatherEntity.fromModel(it) }.orEmpty()
        )
    }
}
