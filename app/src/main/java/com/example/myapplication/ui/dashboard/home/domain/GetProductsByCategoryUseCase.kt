package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.product.ProductsResponse
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepo

class GetProductsByCategoryUseCase(private val remoteRepo: HomeRemoteRepo) : BaseUseCase<ProductsResponse?, ProductsRequest> {
    override suspend fun invoke(params: ProductsRequest): Result<ProductsResponse?> {
        return remoteRepo.getProducts(params)
    }
}