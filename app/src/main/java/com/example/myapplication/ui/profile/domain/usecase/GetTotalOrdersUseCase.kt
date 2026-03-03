package com.example.myapplication.ui.profile.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.model.OrderStatus
import com.example.myapplication.ui.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetTotalOrdersUseCase(private val repo: ProfileRepository) :
    FlowUseCase<List<OrderStatus>?, Unit> {
    override fun invoke(params: Unit): Flow<List<OrderStatus>?> {
        return repo.getTotalOrders()
    }
}