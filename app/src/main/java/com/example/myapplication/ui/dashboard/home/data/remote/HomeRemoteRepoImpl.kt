package com.example.myapplication.ui.dashboard.home.data.remote

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.ProductsResponse

class HomeRemoteRepoImpl(private val context: Context, private val apis: ApiService) : HomeRemoteRepo,
    BaseRemoteRepo(context) {
    override suspend fun getCategories(): List<Category>? {
        val categoryResult = fetch { apis.getCategories() }

        return categoryResult?.toMutableList()?.also {
            it.add(0, Category(name = "all", slug = context.getString(R.string.all)))
        }
    }

    override suspend fun getProducts(request: ProductsRequest?): ProductsResponse? {
        return fetch { apis.getAllProducts(path = request?.getPath() ?: "", map = request?.toMap() ?: hashMapOf()) }
    }
}