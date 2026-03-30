package com.ecommerce.shoppy.ui.order.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.ui.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetUserOrdersUseCase(private val repo: OrderRepository) : FlowUseCase<List<Order>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Order>?> {
        return repo.getUserOrders()
    }
}