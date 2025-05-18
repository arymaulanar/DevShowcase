package com.paopeye.remote.model.responses

import com.paopeye.data.entity.CityAutoCompletesEntity
import com.paopeye.data.mapper.ToEntityMapper

data class ResponseCityAutoCompletes(
    val features: List<ResponseCityAutoComplete>? = null
) : ToEntityMapper<CityAutoCompletesEntity> {
    override fun toEntity() = CityAutoCompletesEntity(
        cities = features?.map { it.toEntity() }
    )
}

