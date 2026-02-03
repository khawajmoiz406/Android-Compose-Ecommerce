package com.example.myapplication.ui.auth.presentation.login

import com.example.myapplication.core.model.User

sealed class LoginEvents {
    class OnLoginSuccess(val user: User): LoginEvents()
    class OnLoginFailed(val error: String): LoginEvents()
}