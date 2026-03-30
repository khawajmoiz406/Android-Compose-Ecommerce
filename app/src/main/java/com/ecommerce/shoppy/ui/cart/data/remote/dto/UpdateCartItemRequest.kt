package com.ecommerce.shoppy.ui.cart.data.remote.dto

data class UpdateCartItemRequest(
    val productId: Int,
    val quantity: Int
)