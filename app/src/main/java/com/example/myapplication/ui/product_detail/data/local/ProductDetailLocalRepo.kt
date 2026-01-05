package com.example.myapplication.ui.product_detail.data.local

import com.example.myapplication.models.response.product.Product

interface ProductDetailLocalRepo {
    suspend fun saveProduct(product: Product)
    suspend fun getProduct(productId: String): Product?
}