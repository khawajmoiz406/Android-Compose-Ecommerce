package com.ecommerce.shoppy.ui.auth.domain.repository

import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.auth.data.remote.dto.LoginRequest
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: LoginRequest): Result<User>
}