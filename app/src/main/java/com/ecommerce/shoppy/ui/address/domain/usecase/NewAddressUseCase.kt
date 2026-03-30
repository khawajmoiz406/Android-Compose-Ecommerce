package com.ecommerce.shoppy.ui.address.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.address.domain.repository.AddressRepository

class NewAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Unit?, Address> {
    override suspend fun invoke(params: Address): Result<Unit?> {
        return repo.addNewAddress(params)
    }
}