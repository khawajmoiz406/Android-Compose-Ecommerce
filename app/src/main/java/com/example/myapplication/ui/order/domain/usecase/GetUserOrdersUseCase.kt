package com.example.myapplication.ui.order.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.model.Order
import com.example.myapplication.ui.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetUserOrdersUseCase(private val repo: OrderRepository) : FlowUseCase<List<Order>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Order>?> {
        return repo.getUserOrders()
    }
}