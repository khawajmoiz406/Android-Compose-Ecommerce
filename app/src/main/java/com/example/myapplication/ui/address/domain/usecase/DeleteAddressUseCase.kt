package com.example.myapplication.ui.address.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.ui.address.domain.repository.AddressRepository

class DeleteAddressUseCase(private val repo: AddressRepository) : SuspendUseCase<Unit?, Int> {
    override suspend fun invoke(params: Int): Result<Unit?> {
        return repo.deleteAddress(params)
    }
}