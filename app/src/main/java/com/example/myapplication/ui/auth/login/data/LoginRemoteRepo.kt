package com.example.myapplication.ui.auth.login.data

import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User

interface LoginRemoteRepo {
    suspend fun login(request: LoginRequest): Result<User>
}