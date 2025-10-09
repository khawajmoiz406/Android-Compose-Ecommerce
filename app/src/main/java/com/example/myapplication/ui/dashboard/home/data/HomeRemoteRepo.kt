package com.example.myapplication.ui.dashboard.home.data

import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.product.ProductsResponse

interface HomeRemoteRepo {
    suspend fun getHome(): Result<HomeResponse?>
    suspend fun getProducts(): Result<ProductsResponse?>
}