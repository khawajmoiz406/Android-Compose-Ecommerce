package com.example.myapplication.core.shared.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.shared.domain.repository.FavouriteRepository

class ToggleFavouriteUseCase(private val repo: FavouriteRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.toggleFavourite(params)
    }
}