package com.ecommerce.shoppy.ui.cart.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.ui.cart.data.remote.dto.CheckoutRequest
import com.ecommerce.shoppy.ui.cart.domain.repository.CheckoutRepository

class CheckoutUseCase(private val repo: CheckoutRepository) : SuspendUseCase<Int?, CheckoutRequest> {
    override suspend fun invoke(params: CheckoutRequest): Result<Int?> {
        return repo.checkout(params)
    }
}