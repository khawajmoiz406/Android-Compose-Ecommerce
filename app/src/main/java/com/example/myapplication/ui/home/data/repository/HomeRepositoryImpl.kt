package com.example.myapplication.ui.home.data.repository

import com.example.myapplication.core.model.Product
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest
import com.example.myapplication.ui.home.data.local.HomeLocalDataSource
import com.example.myapplication.ui.home.data.local.entity.ProductFavoriteStatus
import com.example.myapplication.ui.home.data.remote.HomeRemoteDataSource
import com.example.myapplication.ui.home.data.remote.dto.HomeResponse
import com.example.myapplication.ui.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val remoteRepo: HomeRemoteDataSource,
    private val localRepo: HomeLocalDataSource
) : HomeRepository {
    override fun observeFavoriteStatus(): Flow<List<ProductFavoriteStatus>?> {
        return localRepo.observeFavoriteStatus()
    }

    override fun getHome(request: ProductsRequest): Flow<Result<HomeResponse?>> = flow {
        try {
            // Load local data
            val localCategories = localRepo.getCategories().firstOrNull()
            val localProducts = localRepo.getProducts(request).firstOrNull()

            // Cache favourite product IDs
            val favProductIds = localProducts
                ?.filter { it.isFavourite }
                ?.map { it.id }
                ?.toSet() ?: emptySet()

            // Cache cart product IDs
            val cartProductIds = localProducts
                ?.filter { it.addedToCart }
                ?.map { it.id }
                ?.toSet() ?: emptySet()

            // Emit local data if available
            if (localCategories?.isNotEmpty() == true && localProducts?.isNotEmpty() == true) {
                emit(Result.success(HomeResponse(localProducts, localCategories)))
            }

            // Fetch remote data
            val remoteCategories = remoteRepo.getCategories()
            val remoteProductsResponse = remoteRepo.getProducts(request)
            val remoteProducts = remoteProductsResponse?.products

            // Restore favourites and added to cart
            remoteProducts?.onEach {
                it.copy(
                    isFavourite = it.id in favProductIds,
                    addedToCart = it.id in cartProductIds
                )
            }

            // Save to local database
            remoteCategories?.let { localRepo.saveCategories(it) }
            remoteProducts?.let { localRepo.saveProducts(it) }

            // Emit new data
            emit(Result.success(HomeResponse(products = remoteProducts, categories = remoteCategories)))
        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }

    override suspend fun getProductsByCategory(request: ProductsRequest): Result<List<Product>?> = try {
        // Load local data
        val localProducts = localRepo.getProducts(request).firstOrNull()

        // Cache favourite product IDs
        val favProductIds = localProducts
            ?.filter { it.isFavourite }
            ?.map { it.id }
            ?.toSet() ?: emptySet()

        // Cache cart product IDs
        val cartProductIds = localProducts
            ?.filter { it.addedToCart }
            ?.map { it.id }
            ?.toSet() ?: emptySet()

        // Fetch remote data
        val remoteProductsResponse = remoteRepo.getProducts(request)
        val remoteProducts = remoteProductsResponse?.products

        // Restore favourites and added to cart
        remoteProducts?.onEach {
            it.copy(
                isFavourite = it.id in favProductIds,
                addedToCart = it.id in cartProductIds
            )
        }

        // Save to local database
        remoteProducts?.let { localRepo.saveProducts(it) }

        // return new data
        Result.success(remoteProducts)
    } catch (ex: ApiException) {
        Result.failure(ex)
    }

}