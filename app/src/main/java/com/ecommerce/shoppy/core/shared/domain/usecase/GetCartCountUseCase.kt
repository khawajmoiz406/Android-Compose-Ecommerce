package com.ecommerce.shoppy.core.shared.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartCountUseCase(private val repo: CartRepository) : FlowUseCase<Int?, Unit> {
    override fun invoke(params: Unit): Flow<Int?> {
        return repo.getCartCount()
    }
}