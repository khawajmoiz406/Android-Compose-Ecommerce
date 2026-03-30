package com.ecommerce.shoppy.ui.home.domain.repository

import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.ui.home.data.remote.dto.HomeResponse
import com.ecommerce.shoppy.core.shared.data.remote.dto.ProductsRequest
import com.ecommerce.shoppy.ui.home.data.local.entity.ProductFavoriteStatus
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeFavoriteStatus(): Flow<List<ProductFavoriteStatus>?>
    fun getHome(request: ProductsRequest): Flow<Result<HomeResponse?>>
    suspend fun getProductsByCategory(request: ProductsRequest): Result<List<Product>?>
}