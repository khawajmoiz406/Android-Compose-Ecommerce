package com.example.myapplication.ui.cart.data.remote.dto

data class UpdateCartItemRequest(
    val productId: Int,
    val quantity: Int
)