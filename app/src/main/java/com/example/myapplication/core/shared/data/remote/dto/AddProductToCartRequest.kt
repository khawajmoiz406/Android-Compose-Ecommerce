package com.example.myapplication.core.shared.data.remote.dto

import com.example.myapplication.core.model.Product

data class AddProductToCartRequest(
    val product: Product,
    val quantity: Int
)