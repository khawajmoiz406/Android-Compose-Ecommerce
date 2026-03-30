package com.ecommerce.shoppy.ui.address.domain.usecase

import com.ecommerce.shoppy.base.SuspendUseCase
import com.ecommerce.shoppy.ui.address.domain.repository.AddressRepository

class DeleteAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.deleteAddress(params)
    }
}