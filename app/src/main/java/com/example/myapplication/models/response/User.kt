package com.example.myapplication.models.response

import androidx.compose.runtime.Immutable
import java.io.Serializable

@Immutable
data class User(
    val accessToken: String?,
    val email: String?,
    val firstName: String?,
    val gender: String?,
    val id: Int?,
    val image: String?,
    val lastName: String?,
    val refreshToken: String?,
    val username: String?
) : Serializable