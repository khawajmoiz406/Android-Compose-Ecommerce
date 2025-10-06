package com.example.myapplication.models.helper

import java.io.Serializable

data class NavigationItem(
    val name: Int,
    val route: String,
    val activeIcon: Int,
    val inactiveIcon: Int,
) : Serializable
