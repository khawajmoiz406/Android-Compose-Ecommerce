package com.example.myapplication.ui.dashboard.home.data.local

import com.example.myapplication.core.local.room.AppDatabase
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.Product

class HomeLocalRepoImpl(private val database: AppDatabase) : HomeLocalRepo {
    override suspend fun saveProducts(products: List<Product>) {
        return database.getProductDao().insertProducts(products)
    }

    override suspend fun saveCategories(categories: List<Category>) {
        return database.getCategoryDao().insertCategories(categories)
    }

    override suspend fun getCategories(): List<Category>? {
        return database.getCategoryDao().getAllCategories()
    }

    override suspend fun getProducts(request: ProductsRequest?): List<Product>? {
        return database.getProductDao().getProductsDynamically(request?.sortBy, request?.order)
    }
}