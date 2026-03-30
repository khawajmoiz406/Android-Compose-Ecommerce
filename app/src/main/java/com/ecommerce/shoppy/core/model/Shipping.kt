package com.ecommerce.shoppy.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Shipping(
    val id: Int,
    val name: String,
    val desc: String,
    val price: Double
)