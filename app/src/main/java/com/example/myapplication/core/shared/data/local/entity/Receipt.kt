package com.example.myapplication.core.shared.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class Receipt(
    val subtotal: Double,
    val productDiscount: Double,
    val promoDiscount: Double?,
    val vat: Double,
    val platformFees: Double,
    val shippingFee: Double,
    val totalAmount: Double,
)