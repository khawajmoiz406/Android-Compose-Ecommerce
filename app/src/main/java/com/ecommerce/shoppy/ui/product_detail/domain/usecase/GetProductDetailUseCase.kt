package com.ecommerce.shoppy.ui.product_detail.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.ui.product_detail.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(private val repo: ProductDetailRepository) : FlowUseCase<Result<Product?>, String> {
    override fun invoke(params: String): Flow<Result<Product?>> {
        return repo.getProductDetail(params)
    }
}