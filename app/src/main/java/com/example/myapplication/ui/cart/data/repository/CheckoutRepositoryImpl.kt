package com.example.myapplication.ui.cart.data.repository

import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.cart.data.local.CartLocalDataSource
import com.example.myapplication.ui.cart.data.mapper.toOrder
import com.example.myapplication.ui.cart.data.remote.CartRemoteDataSource
import com.example.myapplication.ui.cart.data.remote.dto.CheckoutRequest
import com.example.myapplication.ui.cart.domain.repository.CheckoutRepository

class CheckoutRepositoryImpl(
    private val remoteRepo: CartRemoteDataSource,
    private val localRepo: CartLocalDataSource
) : CheckoutRepository {
    override suspend fun getDefaultAddress(): Result<Address?> = try {
        val defaultAddress = localRepo.getDefaultAddress()
        Result.success(defaultAddress)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun checkout(request: CheckoutRequest): Result<Int?> = try {
        val orderId = localRepo.checkout(request.toOrder())
        localRepo.clearCart()
        Result.success(orderId)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
