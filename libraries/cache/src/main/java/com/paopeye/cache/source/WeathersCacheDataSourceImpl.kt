package com.paopeye.cache.source

import com.google.gson.Gson
import com.paopeye.cache.model.CachedWeathers
import com.paopeye.cache.preferences.AppPreferences
import com.paopeye.cache.preferences.Keys
import com.paopeye.data.datasource.cache.WeathersCacheDataSource
import com.paopeye.data.entity.WeathersEntity
import com.paopeye.kit.util.catchOrDefault

class WeathersCacheDataSourceImpl(
    private val appPreferences: AppPreferences
) : WeathersCacheDataSource {
    override fun getWeathers(): WeathersEntity {
        val json = appPreferences.getString(Keys.WEATHERS)
        val cachedWeathers = catchOrDefault(WeathersEntity()) {
            Gson().fromJson(json, CachedWeathers::class.java).toEntity()
        }
        return cachedWeathers
    }

    override fun setWeathers(entity: WeathersEntity) {
        val json = Gson().toJson(CachedWeathers.fromEntity(entity))
        appPreferences.put(Keys.WEATHERS, json)
    }
}
