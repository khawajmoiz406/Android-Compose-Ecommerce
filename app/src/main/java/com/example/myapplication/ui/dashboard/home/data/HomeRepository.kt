package com.example.myapplication.ui.dashboard.home.data

import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.product.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome(): Flow<Result<HomeResponse?>>
    suspend fun getProducts(request: ProductsRequest): Flow<Result<ProductsResponse?>>
}