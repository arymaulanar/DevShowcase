package com.paopeye.remote.model.responses

import com.paopeye.data.entity.CityAutoCompleteEntity
import com.paopeye.data.mapper.ToEntityMapper
import java.math.BigDecimal

data class ResponseCityAutoComplete(
    val geometry: ResponseGeometry? = null,
    val properties: ResponseProperties? = null,
) : ToEntityMapper<CityAutoCompleteEntity> {
    override fun toEntity() = CityAutoCompleteEntity(
        longitude = geometry?.coordinates?.firstOrNull().toString(),
        latitude = geometry?.coordinates?.lastOrNull().toString(),
        city = properties?.city ?: properties?.name.orEmpty(),
        country = properties?.country.orEmpty(),
        postcode = properties?.postcode.orEmpty()
    )

    data class ResponseGeometry(
        val coordinates: List<BigDecimal>? = null
    )

    data class ResponseProperties(
        val city: String? = null,
        val country: String? = null,
        val name: String? = null,
        val postcode: String? = null,
    )
}
