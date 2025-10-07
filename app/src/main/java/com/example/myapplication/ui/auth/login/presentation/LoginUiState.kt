package com.example.myapplication.ui.auth.login.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "",
    val password: String = "",
)