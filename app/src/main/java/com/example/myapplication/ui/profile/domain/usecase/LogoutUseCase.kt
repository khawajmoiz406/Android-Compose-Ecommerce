package com.example.myapplication.ui.profile.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.profile.domain.repository.ProfileRepository

class LogoutUseCase(private val repo: ProfileRepository) : SuspendUseCase<Unit?, Unit> {
    override suspend fun invoke(params: Unit): Result<Unit?> {
        return repo.logout()
    }
}