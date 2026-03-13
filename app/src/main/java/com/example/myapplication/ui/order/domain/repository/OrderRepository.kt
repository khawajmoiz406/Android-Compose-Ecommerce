package com.example.myapplication.ui.order.domain.repository

import com.example.myapplication.core.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getUserOrders(): Flow<List<Order>?>
    suspend fun getOrderDetail(orderId: Int): Result<Order>
}