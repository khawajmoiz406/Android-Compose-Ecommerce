package com.example.myapplication.core.model

import java.io.Serializable

data class NavigationItem(
    val name: Int,
    val route: Any,
    val icon: String,
    val showToolbar: Boolean = false,
) : Serializable