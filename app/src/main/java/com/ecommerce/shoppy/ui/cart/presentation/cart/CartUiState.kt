package com.ecommerce.shoppy.ui.cart.presentation.cart

import androidx.compose.runtime.Stable
import com.ecommerce.shoppy.config.components.state.FieldState

@Stable
data class CartUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
    val promoFieldState: FieldState = FieldState(),
)