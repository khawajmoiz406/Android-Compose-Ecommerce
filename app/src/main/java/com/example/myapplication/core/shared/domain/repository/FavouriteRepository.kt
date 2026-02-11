package com.example.myapplication.core.shared.domain.repository

import com.example.myapplication.core.model.Product
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getAllFavourites(): Flow<List<Product>?>
    suspend fun toggleFavourite(productId: Int): Result<Unit>

}