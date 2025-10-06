package com.example.myapplication.ui.auth.login.data

import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User

class LoginRemoteRepoImpl(private val apis: ApiService) : LoginRemoteRepo, BaseRemoteRepo() {
    override suspend fun login(request: LoginRequest): Result<User> {
        return fetch { apis.login(request.toMap()) }
    }
}