package com.example.myapplication.ui.auth.login.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User
import com.example.myapplication.ui.auth.login.data.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repo: LoginRepository) : BaseUseCase<User, LoginRequest> {
    override suspend fun invoke(params: LoginRequest): Flow<Result<User>> {
        return repo.login(params)
    }
}