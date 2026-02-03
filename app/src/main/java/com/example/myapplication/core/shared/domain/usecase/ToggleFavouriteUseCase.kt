package com.example.myapplication.core.shared.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.shared.domain.repository.WishlistRepository

class ToggleFavouriteUseCase(private val repo: WishlistRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.toggleFavourite(params)
    }
}