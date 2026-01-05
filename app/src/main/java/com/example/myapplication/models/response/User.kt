package com.example.myapplication.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?
) : Serializable