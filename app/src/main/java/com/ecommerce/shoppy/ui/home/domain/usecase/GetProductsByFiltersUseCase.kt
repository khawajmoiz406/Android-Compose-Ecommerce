package com.ecommerce.shoppy.ui.home.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.shared.data.remote.dto.ProductsRequest
import com.ecommerce.shoppy.ui.home.domain.repository.HomeRepository

class GetProductsByFiltersUseCase(private val repo: HomeRepository) : SuspendUseCase<List<Product>?, ProductsRequest> {
    override suspend fun invoke(params: ProductsRequest): Result<List<Product>?> {
        return repo.getProductsByCategory(params)
    }
}