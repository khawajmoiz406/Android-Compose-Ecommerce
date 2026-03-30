package com.ecommerce.shoppy.ui.profile.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository

class LogoutUseCase(private val repo: ProfileRepository) : SuspendUseCase<Unit?, Unit> {
    override suspend fun invoke(params: Unit): Result<Unit?> {
        return repo.logout()
    }
}