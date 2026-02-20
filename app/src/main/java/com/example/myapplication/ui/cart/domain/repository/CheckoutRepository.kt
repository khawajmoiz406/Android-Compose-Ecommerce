package com.example.myapplication.ui.cart.domain.repository

import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.cart.data.remote.dto.CheckoutRequest

interface CheckoutRepository {
    suspend fun getDefaultAddress(): Result<Address?>
    suspend fun checkout(request: CheckoutRequest): Result<Int?>
}