package com.example.myapplication.ui.address.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetUserAddressUseCase(private val repo: AddressRepository) : FlowUseCase<List<Address>?, Unit> {
    override fun invoke(params: Unit): Flow<List<Address>?> {
        return repo.getUserAddresses()
    }
}