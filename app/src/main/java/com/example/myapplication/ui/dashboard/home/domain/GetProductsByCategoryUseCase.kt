package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.product.ProductsResponse
import com.example.myapplication.ui.dashboard.home.data.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetProductsByCategoryUseCase(private val repo: HomeRepository) : BaseUseCase<ProductsResponse?, ProductsRequest> {
    override suspend fun invoke(params: ProductsRequest): Flow<Result<ProductsResponse?>> {
        return repo.getProducts(params)
    }
}