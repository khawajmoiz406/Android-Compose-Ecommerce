package com.example.myapplication.ui.cart.presentation

import androidx.compose.runtime.Stable

@Stable
data class CartUiState(
    val error: String = "",
    val isLoading: Boolean = false,
)