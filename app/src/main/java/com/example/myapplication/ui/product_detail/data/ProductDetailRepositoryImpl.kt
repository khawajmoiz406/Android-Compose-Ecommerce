package com.example.myapplication.ui.product_detail.data

import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.product_detail.data.local.ProductDetailLocalRepo
import com.example.myapplication.ui.product_detail.data.remote.ProductDetailRemoteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductDetailRepositoryImpl(
    private val remoteRepo: ProductDetailRemoteRepo,
    private val localRepo: ProductDetailLocalRepo
) : ProductDetailRepository {
    override suspend fun getProductDetail(productId: String): Flow<Result<Product?>> = flow {
        try {
            // Load locally stored product
            var product = localRepo.getProduct(productId)

            // Emit if data is available
            product?.let { emit(Result.success(it)) }

            // Fetch remote product detail
            product = remoteRepo.getProductDetail(productId)

            // Save to local database if not null
            product?.let { localRepo.saveProduct(it) }

            // Emit remote data
            emit(Result.success(product))

        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }
}