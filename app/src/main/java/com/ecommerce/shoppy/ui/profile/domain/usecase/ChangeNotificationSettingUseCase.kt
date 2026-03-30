package com.ecommerce.shoppy.ui.profile.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository

class ChangeNotificationSettingUseCase(private val repo: ProfileRepository) :
    SuspendUseCase<User?, Boolean> {
    override suspend fun invoke(params: Boolean): Result<User?> {
        return repo.changeNotificationSettings(params)
    }
}