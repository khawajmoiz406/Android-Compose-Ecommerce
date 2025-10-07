package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepo

class GetProductsUseCase(private val remoteRepo: HomeRemoteRepo) : BaseUseCase<List<Product>?, Unit> {
    override suspend fun invoke(params: Unit): Result<List<Product>?> {
        return remoteRepo.getProducts()
    }
}