package com.example.myapplication.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Shipping(
    val id: Int,
    val name: String,
    val desc: String,
    val price: Double
)