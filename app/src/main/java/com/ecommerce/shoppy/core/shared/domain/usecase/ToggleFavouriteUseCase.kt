package com.ecommerce.shoppy.core.shared.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.shared.domain.repository.FavouriteRepository

class ToggleFavouriteUseCase(private val repo: FavouriteRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.toggleFavourite(params)
    }
}