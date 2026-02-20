package com.example.myapplication.ui.cart.presentation.checkout

import androidx.compose.runtime.Stable

@Stable
data class CheckoutUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
)