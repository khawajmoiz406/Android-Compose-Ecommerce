package com.ecommerce.shoppy.ui.home.data.remote

import android.content.Context
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.base.BaseDataSource
import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.ProductsResponse
import com.ecommerce.shoppy.core.remote.ApiService
import com.ecommerce.shoppy.core.shared.data.remote.dto.ProductsRequest

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