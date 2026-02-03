package com.example.myapplication.ui.auth.presentation.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "emilys",
    val password: String = "emilyspass",
)