package com.example.myapplication.ui.dashboard.home.data.remote

import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.ProductsResponse

interface HomeRemoteRepo {
    suspend fun getCategories(): List<Category>?
    suspend fun getProducts(request: ProductsRequest? = null): ProductsResponse?

}