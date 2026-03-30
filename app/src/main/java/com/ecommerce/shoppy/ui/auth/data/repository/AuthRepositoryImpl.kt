package com.ecommerce.shoppy.ui.auth.data.repository

import com.ecommerce.shoppy.core.remote.ApiException
import com.ecommerce.shoppy.ui.auth.data.local.AuthLocalDataSource
import com.ecommerce.shoppy.ui.auth.data.remote.AuthRemoteDataSource
import com.ecommerce.shoppy.ui.auth.data.remote.dto.LoginRequest
import com.ecommerce.shoppy.ui.auth.domain.repository.LoginRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : LoginRepository {
    override suspend fun login(request: LoginRequest) = try {
        val user = remoteDataSource.login(request)
        localDataSource.saveUserModel(user)

        Result.success(user)
    } catch (ex: ApiException) {
        Result.failure(ex)
    }
}