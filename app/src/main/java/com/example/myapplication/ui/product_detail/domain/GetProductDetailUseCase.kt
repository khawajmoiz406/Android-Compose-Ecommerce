package com.example.myapplication.ui.product_detail.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.product_detail.data.ProductDetailRepository
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(private val repo: ProductDetailRepository) : BaseUseCase<Product?, String> {
    override suspend fun invoke(params: String): Flow<Result<Product?>> {
        return repo.getProductDetail(params)
    }
}