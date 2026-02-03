package com.example.myapplication.ui.home.data.remote

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.base.BaseDataSource
import com.example.myapplication.core.model.Category
import com.example.myapplication.core.model.ProductsResponse
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest

class HomeRemoteDataSource(private val context: Context, private val apis: ApiService) : BaseDataSource(context) {
    suspend fun getCategories(): List<Category>? {
        val categoryResult = fetch { apis.getCategories() }

        return categoryResult?.toMutableList()?.also {
            it.add(0, Category(slug = "all", name = context.getString(R.string.all)))
        }
    }

    suspend fun getProducts(request: ProductsRequest?): ProductsResponse? {
        return fetch { apis.getAllProducts(path = request?.getPath() ?: "", map = request?.toMap() ?: hashMapOf()) }
    }
}