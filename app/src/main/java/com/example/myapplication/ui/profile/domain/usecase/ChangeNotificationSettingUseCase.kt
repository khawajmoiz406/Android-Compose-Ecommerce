package com.example.myapplication.ui.profile.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.profile.domain.repository.ProfileRepository

class ChangeNotificationSettingUseCase(private val repo: ProfileRepository) :
    SuspendUseCase<User?, Boolean> {
    override suspend fun invoke(params: Boolean): Result<User?> {
        return repo.changeNotificationSettings(params)
    }
}