package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.CityAutoCompletes
import com.paopeye.kit.extension.orEmpty

data class CityAutoCompletesEntity(
    val cities: List<CityAutoCompleteEntity>? = null
) : ToModelMapper<CityAutoCompletes> {
    override fun toModel() = CityAutoCompletes(
        cities = cities?.map { it.toModel() }.orEmpty()
    )

    companion object : FromModelMapper<CityAutoCompletes, CityAutoCompletesEntity> {
        override fun fromModel(model: CityAutoCompletes) = CityAutoCompletesEntity(
            cities = model.cities.map { CityAutoCompleteEntity.fromModel(it) }
        )
    }
}
