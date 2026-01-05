package com.example.myapplication.ui.product_detail.data.remote

import com.example.myapplication.models.response.product.Product

interface ProductDetailRemoteRepo {
    suspend fun getProductDetail(productId: String): Product?
}