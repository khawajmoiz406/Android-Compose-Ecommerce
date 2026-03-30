package com.ecommerce.shoppy.core.shared.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository

class RemoveFromCartUseCase(private val repo: CartRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.removeProductFromCart(params)
    }
}