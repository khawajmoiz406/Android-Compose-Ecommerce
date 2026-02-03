package com.example.myapplication.ui.cart.data.mapper

import com.example.myapplication.core.shared.data.remote.dto.AddProductToCartRequest
import com.example.myapplication.ui.cart.data.local.entity.CartItem

fun AddProductToCartRequest.toCartItem(): CartItem = CartItem(
    productId = product.id!!,
    quantity = quantity
)