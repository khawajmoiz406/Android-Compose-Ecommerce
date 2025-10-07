package com.example.myapplication.ui.dashboard.home.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String = "",
)