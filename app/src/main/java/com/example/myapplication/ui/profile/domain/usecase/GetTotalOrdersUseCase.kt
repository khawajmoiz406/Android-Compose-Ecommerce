package com.example.myapplication.ui.profile.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.ui.cart.data.remote.dto.OrderStatus
import com.example.myapplication.ui.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetTotalOrdersUseCase(private val repo: ProfileRepository) :
    FlowUseCase<Int?, OrderStatus> {
    override fun invoke(params: OrderStatus): Flow<Int?> {
        return repo.getTotalOrders(params)
    }
}