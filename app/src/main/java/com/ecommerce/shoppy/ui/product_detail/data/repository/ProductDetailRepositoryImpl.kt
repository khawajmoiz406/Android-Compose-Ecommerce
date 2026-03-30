package com.ecommerce.shoppy.ui.product_detail.data.repository

import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.remote.ApiException
import com.ecommerce.shoppy.ui.product_detail.data.local.ProductDetailLocalDataSource
import com.ecommerce.shoppy.ui.product_detail.data.remote.ProductDetailRemoteDataSource
import com.ecommerce.shoppy.ui.product_detail.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductDetailRepositoryImpl(
    private val remoteRepo: ProductDetailRemoteDataSource,
    private val localRepo: ProductDetailLocalDataSource
) : ProductDetailRepository {
    override fun getProductDetail(productId: String): Flow<Result<Product?>> = flow {
        try {
            // Load locally stored product
            var product = localRepo.getProduct(productId)

            // Emit if data is available
            product?.let { emit(Result.success(it)) }

            // Fetch remote product detail
            product = remoteRepo.getProductDetail(productId)?.copy(
                addedToCart = product?.addedToCart ?: false,
                isFavourite = product?.isFavourite ?: false
            )

            // Save to local database if not null
            product?.let { localRepo.saveProduct(it) }

            // Emit remote data
            emit(Result.success(product))

        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }
}