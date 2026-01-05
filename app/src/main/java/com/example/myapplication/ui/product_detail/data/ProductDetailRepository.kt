package com.example.myapplication.ui.product_detail.data

import com.example.myapplication.models.response.product.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: String): Flow<Result<Product?>>
}