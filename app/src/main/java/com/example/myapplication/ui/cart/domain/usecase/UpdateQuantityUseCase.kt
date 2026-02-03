package com.example.myapplication.ui.cart.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.shared.domain.repository.CartRepository
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest

class UpdateQuantityUseCase(private val repo: CartRepository) : SuspendUseCase<Unit, UpdateCartItemRequest> {
    override suspend fun invoke(params: UpdateCartItemRequest): Result<Unit> {
        return repo.updateQuantity(params)
    }
}