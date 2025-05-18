package com.paopeye.domain.usecase.photon

import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.domain.repository.PhotonRepository

class GetCityAutoCompletesUseCase(private val repository: PhotonRepository) {
    suspend fun invoke(request: CityAutoComplete) = repository.getCityAutoCompletes(request)
}
