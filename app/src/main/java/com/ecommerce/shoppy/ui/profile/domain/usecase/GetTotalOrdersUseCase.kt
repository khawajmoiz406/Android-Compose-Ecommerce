package com.ecommerce.shoppy.ui.profile.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetTotalOrdersUseCase(private val repo: ProfileRepository) :
    FlowUseCase<List<OrderStatus>?, Unit> {
    override fun invoke(params: Unit): Flow<List<OrderStatus>?> {
        return repo.getTotalOrders()
    }
}