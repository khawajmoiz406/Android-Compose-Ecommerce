package com.ecommerce.shoppy.ui.order.presentation.detail

import androidx.compose.runtime.Stable

@Stable
data class OrderDetailUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
)