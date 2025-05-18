package com.paopeye.remote.model.requests

import com.paopeye.data.entity.CityAutoCompleteEntity
import com.paopeye.data.mapper.FromEntityMapper

data class RequestCityAutoComplete(
    val latitude: String? = null,
    val longitude: String? = null,
    val query: String? = null,
) {
    fun toQuery(): Map<String, String> {
        val queryMap = mutableMapOf("q" to query.orEmpty())
        if (latitude != null && longitude != null) {
            queryMap["lat"] = latitude
            queryMap["lon"] = longitude
        }
        queryMap["limit"] = "10"
        return queryMap
    }

    companion object : FromEntityMapper<CityAutoCompleteEntity, RequestCityAutoComplete> {
        override fun fromEntity(entity: CityAutoCompleteEntity?) = RequestCityAutoComplete(
            latitude = entity?.latitude,
            longitude = entity?.longitude,
            query = entity?.query
        )
    }
}
