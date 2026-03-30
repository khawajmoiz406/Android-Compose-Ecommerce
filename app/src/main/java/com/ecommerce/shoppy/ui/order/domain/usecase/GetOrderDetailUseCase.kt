package com.ecommerce.shoppy.ui.order.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.ui.order.domain.repository.OrderRepository

class GetOrderDetailUseCase(private val repo: OrderRepository) : SuspendUseCase<Order, Int> {
    override suspend fun invoke(params: Int): Result<Order> {
        return repo.getOrderDetail(params)
    }
}