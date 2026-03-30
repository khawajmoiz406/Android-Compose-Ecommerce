package com.ecommerce.shoppy.ui.order.data.local

import com.ecommerce.shoppy.core.local.AppDatabase
import com.ecommerce.shoppy.core.model.Order
import kotlinx.coroutines.flow.Flow

class OrderLocalDataSource(private val database: AppDatabase) {
    fun getUserOrders(): Flow<List<Order>?> {
        return database.getOrderDao().getUserOrders()
    }

    suspend fun getOrderDetail(orderId: Int): Order {
        return database.getOrderDao().getOrderDetail(orderId)
    }
}