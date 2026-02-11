package com.example.myapplication.ui.favourite.data.repository

import com.example.myapplication.core.model.Product
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.core.shared.domain.repository.FavouriteRepository
import com.example.myapplication.ui.favourite.data.local.FavouriteLocalDataSource
import com.example.myapplication.ui.favourite.data.remote.FavouriteRemoteDataSource
import kotlinx.coroutines.flow.Flow

class FavouriteRepositoryImpl(
    private val localDataSource: FavouriteLocalDataSource,
    private val remoteDataSource: FavouriteRemoteDataSource
) : FavouriteRepository {
    override fun getAllFavourites(): Flow<List<Product>?> {
        return localDataSource.getAllFavourites()
    }

    override suspend fun toggleFavourite(productId: Int): Result<Unit> = try {
        Result.success(localDataSource.toggleFavourite(productId))
    } catch (ex: ApiException) {
        Result.failure(ex)
    }
}