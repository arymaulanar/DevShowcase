package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.domain.model.CityAutoCompletes

interface PhotonRepository {
    suspend fun getCityAutoCompletes(request: CityAutoComplete): DataState<CityAutoCompletes>
}
