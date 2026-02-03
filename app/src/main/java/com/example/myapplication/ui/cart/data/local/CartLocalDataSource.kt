package com.example.myapplication.ui.cart.data.local

import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.Cart
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest
import kotlinx.coroutines.flow.Flow

class CartLocalDataSource(private val database: AppDatabase) {
    fun getCartCount(): Flow<Int> {
        return database.getCartDao().getCartCount()
    }

    suspend fun getCart(): Cart {
        val cartItemsWithProduct = database.getCartDao().getCartItems()
        return Cart(items = cartItemsWithProduct)
    }

    suspend fun addToCart(cartItem: CartItem) {
        return database.getCartDao().insertCartItem(cartItem)
    }

    suspend fun removeFromCart(productId: Int) {
        return database.getCartDao().removeCartItem(productId)
    }

    suspend fun updateQuantity(requestParams: UpdateCartItemRequest) {
        return database.getCartDao().updateQuantity(requestParams.productId, requestParams.quantity)
    }
}