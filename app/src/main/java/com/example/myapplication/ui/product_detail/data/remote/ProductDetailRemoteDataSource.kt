package com.example.myapplication.ui.product_detail.data.remote

import android.content.Context
import com.example.myapplication.base.BaseDataSource
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.remote.ApiService

class ProductDetailRemoteDataSource(context: Context, private val apis: ApiService) : BaseDataSource(context) {
    suspend fun getProductDetail(productId: String): Product? {
        return fetch { apis.getProductDetail(id = productId) }
    }
}