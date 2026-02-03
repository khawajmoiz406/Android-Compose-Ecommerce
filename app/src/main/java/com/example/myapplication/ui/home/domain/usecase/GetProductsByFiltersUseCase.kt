package com.example.myapplication.ui.home.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest
import com.example.myapplication.ui.home.domain.repository.HomeRepository

class GetProductsByFiltersUseCase(private val repo: HomeRepository) : SuspendUseCase<List<Product>?, ProductsRequest> {
    override suspend fun invoke(params: ProductsRequest): Result<List<Product>?> {
        return repo.getProductsByCategory(params)
    }
}