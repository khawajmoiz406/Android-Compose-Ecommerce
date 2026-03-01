package com.example.myapplication.ui.profile.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.profile.domain.repository.ProfileRepository

class ChangeThemeModeUseCase(private val repo: ProfileRepository) :
    SuspendUseCase<User?, ThemeMode> {
    override suspend fun invoke(params: ThemeMode): Result<User?> {
        return repo.changeThemeMode(params)
    }
}