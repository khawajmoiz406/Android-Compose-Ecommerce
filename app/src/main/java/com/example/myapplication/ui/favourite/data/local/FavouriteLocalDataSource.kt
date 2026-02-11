package com.example.myapplication.ui.favourite.data.local

import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.Product
import kotlinx.coroutines.flow.Flow

class FavouriteLocalDataSource(private val database: AppDatabase) {
    fun getAllFavourites(): Flow<List<Product>?> {
        return database.getProductDao().getFavouriteProducts()
    }

    suspend fun toggleFavourite(productId: Int) {
        return database.getProductDao().toggleFavourite(productId)
    }
}