package com.ecommerce.shoppy.ui.order.domain.repository

import com.ecommerce.shoppy.core.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getUserOrders(): Flow<List<Order>?>
    suspend fun getOrderDetail(orderId: Int): Result<Order>
}