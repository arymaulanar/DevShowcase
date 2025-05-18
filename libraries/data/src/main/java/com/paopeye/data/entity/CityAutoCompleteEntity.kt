package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.CityAutoComplete

data class CityAutoCompleteEntity(
    val latitude: String? = null,
    val longitude: String? = null,
    val city: String? = null,
    val country: String? = null,
    val postcode: String? = null,
    val query: String? = null,
) : ToModelMapper<CityAutoComplete> {
    override fun toModel() = CityAutoComplete(
        latitude = latitude.orEmpty(),
        longitude = longitude.orEmpty(),
        city = city.orEmpty(),
        country = country.orEmpty(),
        postcode = postcode.orEmpty(),
        query = query.orEmpty(),
    )

    companion object : FromModelMapper<CityAutoComplete, CityAutoCompleteEntity> {
        override fun fromModel(model: CityAutoComplete) = CityAutoCompleteEntity(
            latitude = model.latitude,
            longitude = model.longitude,
            city = model.city,
            country = model.country,
            postcode = model.postcode,
            query = model.query,
        )
    }
}

