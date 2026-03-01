package com.example.myapplication.ui.address.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.address.domain.repository.AddressRepository

class GetAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Address?, Int> {
    override suspend fun invoke(params: Int): Result<Address?> {
        return repo.getAddress(params)
    }
}