package com.ecommerce.shoppy.ui.cart.data.mapper

import com.ecommerce.shoppy.core.shared.data.remote.dto.AddProductToCartRequest
import com.ecommerce.shoppy.ui.cart.data.local.entity.CartItem

fun AddProductToCartRequest.toCartItem(): CartItem = CartItem(
    productId = product.id!!,
    quantity = quantity
)