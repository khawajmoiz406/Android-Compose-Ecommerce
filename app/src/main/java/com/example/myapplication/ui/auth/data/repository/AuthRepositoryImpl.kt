package com.example.myapplication.ui.auth.data.repository

import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.ui.auth.data.local.AuthLocalDataSource
import com.example.myapplication.ui.auth.data.remote.AuthRemoteDataSource
import com.example.myapplication.ui.auth.data.remote.dto.LoginRequest
import com.example.myapplication.ui.auth.domain.repository.LoginRepository

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