package com.example.myapplication.ui.product_detail.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.model.Product
import com.example.myapplication.ui.product_detail.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(private val repo: ProductDetailRepository) : FlowUseCase<Result<Product?>, String> {
    override fun invoke(params: String): Flow<Result<Product?>> {
        return repo.getProductDetail(params)
    }
}