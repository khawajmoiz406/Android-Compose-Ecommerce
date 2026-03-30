package com.ecommerce.shoppy.ui.product_detail.data.remote

import android.content.Context
import com.ecommerce.shoppy.base.BaseDataSource
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.remote.ApiService

class ProductDetailRemoteDataSource(context: Context, private val apis: ApiService) : BaseDataSource(context) {
    suspend fun getProductDetail(productId: String): Product? {
        return fetch { apis.getProductDetail(id = productId) }
    }
}