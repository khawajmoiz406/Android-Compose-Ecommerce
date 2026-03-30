package com.ecommerce.shoppy.ui.profile.presentation

import androidx.compose.runtime.Stable

@Stable
data class ProfileUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val loadingTheme: Boolean = false,
    val loadingNotification: Boolean = false,
    val loggingOut: Boolean = false,
)