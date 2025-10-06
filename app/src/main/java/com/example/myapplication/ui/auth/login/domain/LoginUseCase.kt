package com.example.myapplication.ui.auth.login.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User
import com.example.myapplication.ui.auth.login.data.LoginRemoteRepo

class LoginUseCase(private val remoteRepo: LoginRemoteRepo) : BaseUseCase<User, LoginRequest> {
    override suspend fun invoke(params: LoginRequest): Result<User> {
        return remoteRepo.login(params)
    }
}