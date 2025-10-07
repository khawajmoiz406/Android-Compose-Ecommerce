package com.example.myapplication.ui.dashboard.home.data

import com.example.myapplication.models.response.product.Product

interface HomeRemoteRepo {
    suspend fun getProducts(): Result<List<Product>?>
}