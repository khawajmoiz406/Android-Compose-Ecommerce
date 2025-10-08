package com.example.myapplication.ui.dashboard.home.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)