package com.example.myapplication.ui.auth.login.data.remote

import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User

interface LoginRemoteRepo {
    suspend fun login(request: LoginRequest): User
}