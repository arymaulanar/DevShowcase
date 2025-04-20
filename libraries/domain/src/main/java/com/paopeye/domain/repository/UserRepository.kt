package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.User

interface UserRepository {
    suspend fun getUser(request: User): DataState<User>
}
