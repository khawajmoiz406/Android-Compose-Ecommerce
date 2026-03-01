package com.example.myapplication.ui.address.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.address.domain.repository.AddressRepository

class NewAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Unit?, Address> {
    override suspend fun invoke(params: Address): Result<Unit?> {
        return repo.addNewAddress(params)
    }
}