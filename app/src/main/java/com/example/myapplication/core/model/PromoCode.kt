package com.example.myapplication.core.model

import java.io.Serializable

data class PromoCode(
    val id: Int,
    val name: String,
    val discountPrice: Double
) : Serializable