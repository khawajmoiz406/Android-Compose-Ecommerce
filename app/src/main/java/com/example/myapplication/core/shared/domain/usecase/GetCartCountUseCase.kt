package com.example.myapplication.core.shared.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.shared.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartCountUseCase(private val repo: CartRepository) : FlowUseCase<Int?, Unit> {
    override fun invoke(params: Unit): Flow<Int?> {
        return repo.getCartCount()
    }
}