package com.example.myapplication.ui.dashboard.home.data

import android.content.Context
import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.response.product.Product

class HomeRemoteRepoImpl(context: Context, private val apis: ApiService) : HomeRemoteRepo,
    BaseRemoteRepo(context) {
    override suspend fun getProducts(): Result<List<Product>?> {
        return fetch { apis.getProducts() }
    }
}