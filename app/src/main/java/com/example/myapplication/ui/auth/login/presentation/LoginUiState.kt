package com.example.myapplication.ui.auth.login.presentation

data class LoginUiState(
    var isLoading: Boolean = false,
    var error: String = "",
    var email: String = "",
    var password: String = "",
)