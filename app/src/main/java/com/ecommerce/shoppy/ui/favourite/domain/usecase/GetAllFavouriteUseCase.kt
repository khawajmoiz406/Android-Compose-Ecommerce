package com.ecommerce.shoppy.ui.favourite.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.shared.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavouriteUseCase(private val repo: FavouriteRepository) : FlowUseCase<List<Product>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Product>?> {
        return repo.getAllFavourites()
    }
}