package com.example.myapplication.ui.dashboard.home.data

import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.product.ProductsResponse
import com.example.myapplication.ui.dashboard.home.data.local.HomeLocalRepo
import com.example.myapplication.ui.dashboard.home.data.remote.HomeRemoteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val remoteRepo: HomeRemoteRepo,
    private val localRepo: HomeLocalRepo
) : HomeRepository {
    override suspend fun getHome(): Flow<Result<HomeResponse?>> = flow {
        // Load local data
        val localCategories = localRepo.getCategories()
        val localProducts = localRepo.getProducts()

        // Cache favourite product IDs
        val favProductIds = localProducts
            ?.filter { it.isFavourite == true }
            ?.map { it.id }
            ?.toSet()
            .orEmpty()

        // Emit local data if available
        if (!localCategories.isNullOrEmpty() && !localProducts.isNullOrEmpty()) {
            emit(Result.success(HomeResponse(localProducts, localCategories)))
        }

        try {
            // Fetch remote data
            val remoteCategories = remoteRepo.getCategories()
            val remoteProductsResponse = remoteRepo.getProducts()
            val remoteProducts = remoteProductsResponse?.products

            // Restore favourites
            remoteProducts?.onEach { it.isFavourite = it.id in favProductIds }

            // Save to local database
            remoteCategories?.let { localRepo.saveCategories(it) }
            remoteProducts?.let { localRepo.saveProducts(it) }

            // Emit remote data
            emit(Result.success(HomeResponse(products = remoteProducts, categories = remoteCategories)))
        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }

    override suspend fun getProducts(request: ProductsRequest): Flow<Result<ProductsResponse?>> = flow {
        val products = remoteRepo.getProducts(request)

        try {
            emit(Result.success(products))
        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }
}