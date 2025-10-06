package com.example.myapplication.models.request

import com.example.myapplication.base.BaseRequest

data class LoginRequest(
    val email: String,
    val password: String
) : BaseRequest() {
    override fun toMap(): Map<String, String> = mapOf(
        "email" to email,
        "password" to password
    )

}