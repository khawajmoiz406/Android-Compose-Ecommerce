package com.example.myapplication.ui.cart.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.cart.domain.repository.CheckoutRepository

class GetDefaultAddressUseCase(private val repo: CheckoutRepository) : SuspendUseCase<Address?, Unit> {
    override suspend fun invoke(params: Unit): Result<Address?> {
        return repo.getDefaultAddress()
    }
}