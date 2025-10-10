package com.example.myapplication.models.helper

import java.io.Serializable

data class NavigationItem(
    val name: Int,
    val route: String,
    val icon: String,
) : Serializable
