package com.ecommerce.shoppy.core.shared.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.shared.data.remote.dto.AddProductToCartRequest
import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository

class AddToCartUseCase(private val repo: CartRepository) : SuspendUseCase<Unit?, AddProductToCartRequest> {
    override suspend fun invoke(params: AddProductToCartRequest): Result<Unit?> {
        return repo.addProductToCart(params)
    }
}