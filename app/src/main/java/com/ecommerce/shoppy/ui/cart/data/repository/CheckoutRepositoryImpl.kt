package com.ecommerce.shoppy.ui.cart.data.repository

import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.cart.data.local.CartLocalDataSource
import com.ecommerce.shoppy.ui.cart.data.mapper.toOrder
import com.ecommerce.shoppy.ui.cart.data.remote.CartRemoteDataSource
import com.ecommerce.shoppy.ui.cart.data.remote.dto.CheckoutRequest
import com.ecommerce.shoppy.ui.cart.domain.repository.CheckoutRepository

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
        Result.success(localRepo.checkout(request.toOrder()))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
