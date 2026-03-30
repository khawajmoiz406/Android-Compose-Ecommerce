package com.ecommerce.shoppy.ui.address.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.address.domain.repository.AddressRepository

class GetAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Address?, Int> {
    override suspend fun invoke(params: Int): Result<Address?> {
        return repo.getAddress(params)
    }
}