package com.example.myapplication.ui.home.domain.repository

import com.example.myapplication.core.model.Category
import com.example.myapplication.core.model.Product
import com.example.myapplication.ui.home.data.remote.dto.HomeResponse
import com.example.myapplication.core.model.ProductsResponse
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest
import com.example.myapplication.ui.home.data.local.entity.ProductFavoriteStatus
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeFavoriteStatus(): Flow<List<ProductFavoriteStatus>?>
    fun getHome(request: ProductsRequest): Flow<Result<HomeResponse?>>
    suspend fun getProductsByCategory(request: ProductsRequest): Result<List<Product>?>
}