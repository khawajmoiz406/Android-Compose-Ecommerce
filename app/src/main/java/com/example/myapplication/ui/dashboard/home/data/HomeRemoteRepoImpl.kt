package com.example.myapplication.ui.dashboard.home.data

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.base.BaseRemoteRepo
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.ProductsResponse

class HomeRemoteRepoImpl(private val context: Context, private val apis: ApiService) : HomeRemoteRepo,
    BaseRemoteRepo(context) {
    override suspend fun getProducts(request: ProductsRequest): Result<ProductsResponse?> {
        return fetch { apis.getAllProducts(path = request.getPath(), map = request.toMap()) }
    }

    override suspend fun getHome(): Result<HomeResponse?> {
        val productsResult = fetch { apis.getAllProducts() }
        val categoryResult = fetch { apis.getCategories() }

        if (productsResult.isFailure) return Result.failure<HomeResponse>(productsResult.exceptionOrNull()!!)
        if (categoryResult.isFailure) return Result.failure<HomeResponse>(categoryResult.exceptionOrNull()!!)

        return Result.success(
            HomeResponse(
                products = productsResult.getOrNull()?.products,
                categories = categoryResult.getOrNull()?.toMutableList()?.also {
                    it.add(0, Category("all", context.getString(R.string.all)))
                },
            )
        )
    }
}