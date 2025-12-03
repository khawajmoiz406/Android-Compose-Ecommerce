package com.example.myapplication.ui.product_detail.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class ProductDetailUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)