package com.ecommerce.shoppy.ui.auth.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.auth.data.remote.dto.LoginRequest
import com.ecommerce.shoppy.ui.auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repo: LoginRepository) : SuspendUseCase<User, LoginRequest> {
    override suspend fun invoke(params: LoginRequest): Result<User> {
        return repo.login(params)
    }
}