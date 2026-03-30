package com.ecommerce.shoppy.core.shared.domain.repository

import com.ecommerce.shoppy.core.model.Product
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getAllFavourites(): Flow<List<Product>?>
    suspend fun toggleFavourite(productId: Int): Result<Unit>

}