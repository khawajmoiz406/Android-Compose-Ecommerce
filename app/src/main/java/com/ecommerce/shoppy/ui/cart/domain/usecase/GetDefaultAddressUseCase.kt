package com.ecommerce.shoppy.ui.cart.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.cart.domain.repository.CheckoutRepository

class GetDefaultAddressUseCase(private val repo: CheckoutRepository) : SuspendUseCase<Address?, Unit> {
    override suspend fun invoke(params: Unit): Result<Address?> {
        return repo.getDefaultAddress()
    }
}