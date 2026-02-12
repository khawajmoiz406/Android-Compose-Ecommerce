package com.example.myapplication.ui.product_detail.presentation

import androidx.compose.runtime.Stable

@Stable
data class ProductDetailUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)