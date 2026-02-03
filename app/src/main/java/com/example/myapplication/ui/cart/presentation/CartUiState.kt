package com.example.myapplication.ui.cart.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class CartUiState(
    val error: String = "",
    val isLoading: Boolean = false,
)