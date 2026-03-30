package com.ecommerce.shoppy.ui.home.data.local

import com.ecommerce.shoppy.core.local.AppDatabase
import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.shared.data.remote.dto.ProductsRequest
import com.ecommerce.shoppy.ui.home.data.local.entity.ProductFavoriteStatus
import kotlinx.coroutines.flow.Flow

class HomeLocalDataSource(private val database: AppDatabase) {
    fun getProducts(request: ProductsRequest?): Flow<List<Product>?> {
        return database.getProductDao().getProductsDynamically(request?.sortBy, request?.order)
    }

    fun getCategories(): Flow<List<Category>?> {
        return database.getCategoryDao().getAllCategories()
    }

    fun observeFavoriteStatus(): Flow<List<ProductFavoriteStatus>?> {
        return database.getProductDao().observeFavoriteStatus()
    }

    suspend fun saveProducts(products: List<Product>) {
        return database.getProductDao().insertProducts(products)
    }

    suspend fun saveCategories(categories: List<Category>) {
        return database.getCategoryDao().insertCategories(categories)
    }
}