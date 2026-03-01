package com.example.myapplication.core.model

import androidx.compose.runtime.Stable
import com.example.myapplication.config.theme.ThemeMode
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Stable
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
    val refreshToken: String?,

    //For app use, these fields are not in api response
    val phoneNumber: String? = "",
    val notificationEnabled: Boolean = true,
    val themeMode: Int = ThemeMode.Light.value
) : Serializable