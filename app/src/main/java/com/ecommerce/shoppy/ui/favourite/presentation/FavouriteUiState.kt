package com.ecommerce.shoppy.ui.favourite.presentation

import androidx.compose.runtime.Stable

@Stable
data class FavouriteUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
)