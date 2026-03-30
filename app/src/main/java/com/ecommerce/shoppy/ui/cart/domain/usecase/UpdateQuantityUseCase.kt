package com.ecommerce.shoppy.ui.cart.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository
import com.ecommerce.shoppy.ui.cart.data.remote.dto.UpdateCartItemRequest

class UpdateQuantityUseCase(private val repo: CartRepository) : SuspendUseCase<Unit, UpdateCartItemRequest> {
    override suspend fun invoke(params: UpdateCartItemRequest): Result<Unit> {
        return repo.updateQuantity(params)
    }
}