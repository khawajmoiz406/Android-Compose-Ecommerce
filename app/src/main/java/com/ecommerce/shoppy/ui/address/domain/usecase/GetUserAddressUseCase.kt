package com.ecommerce.shoppy.ui.address.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetUserAddressUseCase(private val repo: AddressRepository) : FlowUseCase<List<Address>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Address>?> {
        return repo.getUserAddresses()
    }
}