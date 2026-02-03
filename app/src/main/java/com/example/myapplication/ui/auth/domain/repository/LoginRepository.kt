package com.example.myapplication.ui.auth.domain.repository

import com.example.myapplication.core.model.User
import com.example.myapplication.ui.auth.data.remote.dto.LoginRequest
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: LoginRequest): Result<User>
}