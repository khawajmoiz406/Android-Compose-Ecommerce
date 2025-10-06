package com.example.myapplication.ui.auth.login.presentation

import com.example.myapplication.models.response.User

sealed class LoginEvents {
    class OnLoginSuccess(val user: User): LoginEvents()
    class OnLoginFailed(val error: String): LoginEvents()
}