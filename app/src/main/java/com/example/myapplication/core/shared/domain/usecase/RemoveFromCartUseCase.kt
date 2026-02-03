package com.example.myapplication.core.shared.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.shared.domain.repository.CartRepository

class RemoveFromCartUseCase(private val repo: CartRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.removeProductFromCart(params)
    }
}