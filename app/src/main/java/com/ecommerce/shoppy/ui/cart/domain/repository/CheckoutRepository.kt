package com.ecommerce.shoppy.ui.cart.domain.repository

import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.cart.data.remote.dto.CheckoutRequest

interface CheckoutRepository {
    suspend fun getDefaultAddress(): Result<Address?>
    suspend fun checkout(request: CheckoutRequest): Result<Int?>
}