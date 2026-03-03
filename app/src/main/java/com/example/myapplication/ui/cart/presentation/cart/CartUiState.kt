package com.example.myapplication.ui.cart.presentation.cart

import androidx.compose.runtime.Stable
import com.example.myapplication.config.components.state.FieldState

@Stable
data class CartUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
    val promoFieldState: FieldState = FieldState(),
)