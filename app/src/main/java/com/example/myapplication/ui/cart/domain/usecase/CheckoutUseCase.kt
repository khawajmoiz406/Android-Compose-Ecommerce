package com.example.myapplication.ui.cart.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.ui.cart.data.remote.dto.CheckoutRequest
import com.example.myapplication.ui.cart.domain.repository.CheckoutRepository

class CheckoutUseCase(private val repo: CheckoutRepository) : SuspendUseCase<Int?, CheckoutRequest> {
    override suspend fun invoke(params: CheckoutRequest): Result<Int?> {
        return repo.checkout(params)
    }
}