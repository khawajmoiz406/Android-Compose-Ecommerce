package com.example.myapplication.models.request

import com.example.myapplication.base.BaseRequest
import com.example.myapplication.utils.Constants

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