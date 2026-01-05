package com.example.myapplication.ui.dashboard.home.data.local

import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.Product

interface HomeLocalRepo {
    suspend fun saveProducts(products: List<Product>)
    suspend fun saveCategories(categories: List<Category>)
    suspend fun getCategories(): List<Category>
    suspend fun getProducts(request: ProductsRequest? = null): List<Product>
}