package com.ecommerce.shoppy.core.shared.domain.repository

import com.ecommerce.shoppy.core.model.Cart
import com.ecommerce.shoppy.core.shared.data.remote.dto.AddProductToCartRequest
import com.ecommerce.shoppy.ui.cart.data.remote.dto.UpdateCartItemRequest
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartCount(): Flow<Int>
    suspend fun getCart(): Result<Cart>
    suspend fun addProductToCart(requestParams: AddProductToCartRequest): Result<Unit?>
    suspend fun removeProductFromCart(productId: Int): Result<Unit?>
    suspend fun updateQuantity(requestParams: UpdateCartItemRequest): Result<Unit>
}