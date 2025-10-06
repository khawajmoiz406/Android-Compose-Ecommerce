package com.example.myapplication.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("username")
    val name: String? = "",
    @SerializedName("email_id")
    val email: String? = "",
    @SerializedName("pass")
    val password: String? = ""
) : Serializable
