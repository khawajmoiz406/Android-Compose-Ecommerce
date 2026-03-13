package com.example.myapplication.ui.order.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Order
import com.example.myapplication.ui.order.domain.repository.OrderRepository

class GetOrderDetailUseCase(private val repo: OrderRepository) : SuspendUseCase<Order, Int> {
    override suspend fun invoke(params: Int): Result<Order> {
        return repo.getOrderDetail(params)
    }
}