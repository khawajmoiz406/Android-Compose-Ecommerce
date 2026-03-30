package com.ecommerce.shoppy.ui.profile.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.config.theme.ThemeMode
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository

class ChangeThemeModeUseCase(private val repo: ProfileRepository) :
    SuspendUseCase<User?, ThemeMode> {
    override suspend fun invoke(params: ThemeMode): Result<User?> {
        return repo.changeThemeMode(params)
    }
}