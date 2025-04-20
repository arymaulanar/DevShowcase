package com.paopeye.domain.usecase.user

import com.paopeye.domain.model.User
import com.paopeye.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend fun invoke(request: User) = repository.getUser(request)
}
