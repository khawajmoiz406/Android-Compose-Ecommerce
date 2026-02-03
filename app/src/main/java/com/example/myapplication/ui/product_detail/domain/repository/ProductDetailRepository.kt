package com.example.myapplication.ui.product_detail.domain.repository

import com.example.myapplication.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    fun getProductDetail(productId: String): Flow<Result<Product?>>
}