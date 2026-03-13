package com.example.myapplication.ui.order.data.local

import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.Order
import kotlinx.coroutines.flow.Flow

class OrderLocalDataSource(private val database: AppDatabase) {
    fun getUserOrders(): Flow<List<Order>?> {
        return database.getOrderDao().getUserOrders()
    }

    suspend fun getOrderDetail(orderId: Int): Order {
        return database.getOrderDao().getOrderDetail(orderId)
    }
}