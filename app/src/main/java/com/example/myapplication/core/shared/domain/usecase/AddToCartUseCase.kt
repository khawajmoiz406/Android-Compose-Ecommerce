package com.example.myapplication.core.shared.domain.usecase

import com.example.myapplication.base.SuspendUseCase
import com.example.myapplication.core.shared.data.remote.dto.AddProductToCartRequest
import com.example.myapplication.core.shared.domain.repository.CartRepository

class AddToCartUseCase(private val repo: CartRepository) : SuspendUseCase<Unit?, AddProductToCartRequest> {
    override suspend fun invoke(params: AddProductToCartRequest): Result<Unit?> {
        return repo.addProductToCart(params)
    }
}