package com.ecommerce.shoppy.ui.order.data.repository

import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.ui.order.data.local.OrderLocalDataSource
import com.ecommerce.shoppy.ui.order.data.remote.OrderRemoteDataSource
import com.ecommerce.shoppy.ui.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val localDataSource: OrderLocalDataSource,
    private val remoteDataSource: OrderRemoteDataSource
) : OrderRepository {
    override fun getUserOrders(): Flow<List<Order>?> {
        return localDataSource.getUserOrders()
    }

    override suspend fun getOrderDetail(orderId: Int): Result<Order> = try {
        Result.success(localDataSource.getOrderDetail(orderId))
    } catch (e: Exception) {
        Result.failure(e)
    }
}