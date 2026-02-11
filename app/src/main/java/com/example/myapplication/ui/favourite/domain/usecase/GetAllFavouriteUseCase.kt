package com.example.myapplication.ui.favourite.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.shared.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavouriteUseCase(private val repo: FavouriteRepository) : FlowUseCase<List<Product>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Product>?> {
        return repo.getAllFavourites()
    }
}