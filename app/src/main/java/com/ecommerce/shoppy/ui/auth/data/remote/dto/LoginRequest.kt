package com.ecommerce.shoppy.ui.auth.data.remote.dto

import com.ecommerce.shoppy.base.BaseRequest
import com.ecommerce.shoppy.config.utils.Constants

data class LoginRequest(
    val email: String,
    val password: String
) : BaseRequest() {
    override fun toMap(): HashMap<String, Any> = hashMapOf(
        "username" to email,
        "password" to password,
        "expiresInMins" to Constants.TOKEN_TIMEOUT,
    )

}