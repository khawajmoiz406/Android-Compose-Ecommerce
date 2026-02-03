package com.example.myapplication.ui.cart.data.repository

import com.example.myapplication.core.shared.data.remote.dto.AddProductToCartRequest
import com.example.myapplication.core.shared.domain.repository.CartRepository
import com.example.myapplication.ui.cart.data.local.CartLocalDataSource
import com.example.myapplication.ui.cart.data.mapper.toCartItem
import com.example.myapplication.ui.cart.data.remote.CartRemoteDataSource
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val remoteRepo: CartRemoteDataSource,
    private val localRepo: CartLocalDataSource
) : CartRepository {
    override fun getCartCount(): Flow<Int> {
        return localRepo.getCartCount()
    }

    override suspend fun getCart() = try {
        Result.success(localRepo.getCart())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun addProductToCart(requestParams: AddProductToCartRequest) = try {
        Result.success(localRepo.addToCart(requestParams.toCartItem()))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun removeProductFromCart(productId: Int) = try {
        Result.success(localRepo.removeFromCart(productId))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateQuantity(requestParams: UpdateCartItemRequest) = try {
        Result.success(localRepo.updateQuantity(requestParams))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
