package com.example.myapplication.ui.favourite.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class FavouriteUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
)