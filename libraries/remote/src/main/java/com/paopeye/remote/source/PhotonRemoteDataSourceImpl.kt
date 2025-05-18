package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.PhotonRemoteDataSource
import com.paopeye.data.entity.CityAutoCompleteEntity
import com.paopeye.data.entity.CityAutoCompletesEntity
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.remote.base.util.mapResponseCode
import com.paopeye.remote.model.requests.RequestCityAutoComplete
import com.paopeye.remote.service.PhotonService

class PhotonRemoteDataSourceImpl(
    private val service: PhotonService
) : PhotonRemoteDataSource {
    override suspend fun getCityAutoCompletes(request: CityAutoCompleteEntity): ResponseEntity<CityAutoCompletesEntity> {
        val response = service.getCityAutoComplete(
            RequestCityAutoComplete.fromEntity(request).toQuery()
        )
        val dataEntity = CityAutoCompletesEntity(
            cities = response.body()?.features?.map { it.toEntity() }
        )
        return ResponseEntity(
            responseCode = mapResponseCode(response.code()),
            data = dataEntity
        )
    }
}
