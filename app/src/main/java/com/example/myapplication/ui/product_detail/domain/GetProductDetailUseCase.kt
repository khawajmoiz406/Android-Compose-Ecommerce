package com.example.myapplication.ui.product_detail.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.product_detail.data.ProductDetailRemoteRepo

class GetProductDetailUseCase(private val remoteRepo: ProductDetailRemoteRepo) : BaseUseCase<Product?, String> {
    override suspend fun invoke(params: String): Result<Product?> {
        return remoteRepo.getProductDetail(params)
    }
}