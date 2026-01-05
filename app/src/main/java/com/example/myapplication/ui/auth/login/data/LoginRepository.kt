package com.example.myapplication.ui.auth.login.data

import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: LoginRequest): Flow<Result<User>>
}