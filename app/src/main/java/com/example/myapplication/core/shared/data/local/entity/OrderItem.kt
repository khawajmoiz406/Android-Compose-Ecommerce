package com.example.myapplication.core.shared.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val discount: Double,
    val originalPrice: Double,
    val discountedPrice: Double,
    val subtotal: Double,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)