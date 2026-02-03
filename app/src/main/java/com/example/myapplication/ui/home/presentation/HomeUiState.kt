package com.example.myapplication.ui.home.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val error: String = "",
    val mainLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val firstApiCalled: Boolean = false,
    val categoryProductsLoading: Boolean = false,
)