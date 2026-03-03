package com.example.myapplication.ui.cart.data.local

import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.Order
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest
import kotlinx.coroutines.flow.Flow

class CartLocalDataSource(private val database: AppDatabase) {
    fun getCartCount(): Flow<Int> {
        return database.getCartDao().getCartCount()
    }

    suspend fun getDefaultAddress(): Address? {
        val defaultAddress = database.getAddressDao().getDefaultAddress()
        return defaultAddress
    }

    suspend fun getCart(): Cart {
        val cartItemsWithProduct = database.getCartDao().getCartItems()
        return Cart(items = cartItemsWithProduct)
    }

    suspend fun addToCart(cartItem: CartItem) {
        database.getCartDao().insertCartItem(cartItem)
        database.getProductDao().toggleCartValue(cartItem.productId)
        return
    }

    suspend fun removeFromCart(productId: Int) {
        database.getCartDao().removeCartItem(productId)
        database.getProductDao().toggleCartValue(productId)
        return
    }

    suspend fun updateQuantity(requestParams: UpdateCartItemRequest) {
        return database.getCartDao().updateQuantity(requestParams.productId, requestParams.quantity)
    }

    suspend fun checkout(order: Order): Int {
        return database.getOrderDao().insertOrder(order).toInt()
    }

    suspend fun clearCart() {
        return database.getCartDao().clearAll()
    }
}