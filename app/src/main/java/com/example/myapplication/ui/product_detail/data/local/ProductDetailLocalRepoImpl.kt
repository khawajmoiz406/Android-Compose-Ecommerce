package com.example.myapplication.ui.product_detail.data.local

import com.example.myapplication.core.local.room.AppDatabase
import com.example.myapplication.models.response.product.Product

class ProductDetailLocalRepoImpl(private val database: AppDatabase) : ProductDetailLocalRepo {
    override suspend fun saveProduct(product: Product) {
        database.getProductDao().insertProduct(product)
    }

    override suspend fun getProduct(productId: String): Product? {
        return database.getProductDao().getProductById(productId.toInt())
    }
}