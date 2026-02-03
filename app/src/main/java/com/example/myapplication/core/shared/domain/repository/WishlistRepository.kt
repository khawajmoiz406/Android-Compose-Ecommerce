package com.example.myapplication.core.shared.domain.repository

import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    suspend fun toggleFavourite(productId: Int): Result<Unit?>

}