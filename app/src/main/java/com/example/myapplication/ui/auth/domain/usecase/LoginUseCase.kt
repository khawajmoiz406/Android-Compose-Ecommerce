package com.example.myapplication.ui.auth.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.auth.data.remote.dto.LoginRequest
import com.example.myapplication.ui.auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repo: LoginRepository) : SuspendUseCase<User, LoginRequest> {
    override suspend fun invoke(params: LoginRequest): Result<User> {
        return repo.login(params)
    }
}