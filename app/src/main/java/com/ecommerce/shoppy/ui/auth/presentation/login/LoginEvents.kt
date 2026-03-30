package com.ecommerce.shoppy.ui.auth.presentation.login

import com.ecommerce.shoppy.core.model.User

sealed class LoginEvents {
    class OnLoginSuccess(val user: User): LoginEvents()
    class OnLoginFailed(val error: String): LoginEvents()
}