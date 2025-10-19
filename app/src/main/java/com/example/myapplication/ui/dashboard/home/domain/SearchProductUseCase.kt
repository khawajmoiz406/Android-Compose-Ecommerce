package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.product.ProductsResponse
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepo

class SearchProductUseCase(private val remoteRepo: HomeRemoteRepo) : BaseUseCase<ProductsResponse?, String> {
    override suspend fun invoke(params: String): Result<ProductsResponse?> {
        return remoteRepo.searchProduct(params)
    }
}