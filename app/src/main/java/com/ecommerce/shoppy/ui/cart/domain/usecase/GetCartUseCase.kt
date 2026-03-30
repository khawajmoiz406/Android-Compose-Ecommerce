package com.ecommerce.shoppy.ui.cart.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Cart
import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository

class GetCartUseCase(private val repo: CartRepository) : SuspendUseCase<Cart?, Unit> {
    override suspend fun invoke(params: Unit): Result<Cart?> {
        return repo.getCart()
    }
}