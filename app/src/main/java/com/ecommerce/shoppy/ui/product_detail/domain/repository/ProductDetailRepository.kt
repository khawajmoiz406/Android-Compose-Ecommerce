package com.ecommerce.shoppy.ui.product_detail.domain.repository

import com.ecommerce.shoppy.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    fun getProductDetail(productId: String): Flow<Result<Product?>>
}