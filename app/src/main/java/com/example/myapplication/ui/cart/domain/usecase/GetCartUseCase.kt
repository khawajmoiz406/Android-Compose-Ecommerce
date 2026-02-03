package com.example.myapplication.ui.cart.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.shared.domain.repository.CartRepository

class GetCartUseCase(private val repo: CartRepository) : SuspendUseCase<Cart?, Unit> {
    override suspend fun invoke(params: Unit): Result<Cart?> {
        return repo.getCart()
    }
}