package com.ecommerce.shoppy.ui.home.presentation

import androidx.compose.runtime.Stable

@Stable
data class HomeUiState(
    val error: String = "",
    val mainLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val firstApiCalled: Boolean = false,
    val categoryProductsLoading: Boolean = false,
)