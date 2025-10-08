package com.example.myapplication.ui.auth.login.data

import android.content.Context
import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User

class LoginRemoteRepoImpl(context: Context, private val apis: ApiService) : LoginRemoteRepo,
    BaseRemoteRepo(context) {
    override suspend fun login(request: LoginRequest): Result<User> {
        return fetch { apis.login(headers = headers, body = request.toMap()) }
    }
}