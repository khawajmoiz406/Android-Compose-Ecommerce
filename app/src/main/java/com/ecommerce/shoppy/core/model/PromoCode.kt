package com.ecommerce.shoppy.core.model

import kotlinx.serialization.Serializable


@Serializable
data class PromoCode(
    val id: Int,
    val name: String,
    val discountPrice: Double
)