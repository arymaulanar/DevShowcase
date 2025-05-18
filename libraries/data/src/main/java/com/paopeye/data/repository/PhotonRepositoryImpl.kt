package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.remote.PhotonRemoteDataSource
import com.paopeye.data.entity.CityAutoCompleteEntity
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.domain.repository.PhotonRepository

class PhotonRepositoryImpl(
    private val photonRemoteDataSource: PhotonRemoteDataSource
) : PhotonRepository {
    override suspend fun getCityAutoCompletes(request: CityAutoComplete) =
        DataStateBoundResource.createNetworkCall {
            photonRemoteDataSource.getCityAutoCompletes(
                CityAutoCompleteEntity.fromModel(request)
            )
        }.getResult()
}
