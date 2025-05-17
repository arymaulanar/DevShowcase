package com.paopeye.data.datasource.cache

import com.paopeye.data.entity.WeathersEntity

interface WeathersCacheDataSource {
    fun getWeathers(): WeathersEntity
    fun setWeathers(entity: WeathersEntity)
}
