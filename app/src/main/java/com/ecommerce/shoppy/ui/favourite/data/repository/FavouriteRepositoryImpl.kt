package com.ecommerce.shoppy.ui.favourite.data.repository

import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.remote.ApiException
import com.ecommerce.shoppy.core.shared.domain.repository.FavouriteRepository
import com.ecommerce.shoppy.ui.favourite.data.local.FavouriteLocalDataSource
import com.ecommerce.shoppy.ui.favourite.data.remote.FavouriteRemoteDataSource
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