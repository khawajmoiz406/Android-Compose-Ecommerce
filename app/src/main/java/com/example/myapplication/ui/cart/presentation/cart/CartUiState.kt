package com.example.myapplication.ui.cart.presentation.cart

import androidx.compose.runtime.Stable

@Stable
data class CartUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
)