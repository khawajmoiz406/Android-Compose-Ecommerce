package com.example.myapplication.ui.product_detail.data

import android.content.Context
import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.response.product.Product

class ProductDetailRemoteRepoImpl(context: Context, private val apis: ApiService) : ProductDetailRemoteRepo,
    BaseRemoteRepo(context) {
    override suspend fun getProductDetail(productId: String): Result<Product?> {
        return fetch { apis.getProductDetail(id = productId) }
    }
}