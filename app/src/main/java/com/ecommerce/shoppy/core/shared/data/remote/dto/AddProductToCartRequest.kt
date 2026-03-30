package com.ecommerce.shoppy.core.shared.data.remote.dto

import com.ecommerce.shoppy.core.model.Product

data class AddProductToCartRequest(
    val product: Product,
    val quantity: Int
)