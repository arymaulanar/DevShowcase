package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.CityAutoCompleteEntity
import com.paopeye.data.entity.CityAutoCompletesEntity
import com.paopeye.data.entity.ResponseEntity

interface PhotonRemoteDataSource {
    suspend fun getCityAutoCompletes(request: CityAutoCompleteEntity): ResponseEntity<CityAutoCompletesEntity>
}
