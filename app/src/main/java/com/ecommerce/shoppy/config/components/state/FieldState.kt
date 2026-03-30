package com.ecommerce.shoppy.config.components.state

import androidx.compose.runtime.Stable

@Stable
data class FieldState(
    val value: String = "",
    val error: Int? = null,
    val isTouched: Boolean = false
)