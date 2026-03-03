package com.example.myapplication.ui.order.data.repository

import com.example.myapplication.core.model.Order
import com.example.myapplication.ui.order.data.local.OrderLocalDataSource
import com.example.myapplication.ui.order.data.remote.OrderRemoteDataSource
import com.example.myapplication.ui.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val localDataSource: OrderLocalDataSource,
    private val remoteDataSource: OrderRemoteDataSource
) : OrderRepository {
    override fun getUserOrders(): Flow<List<Order>?> {
        return localDataSource.getUserOrders()
    }
}