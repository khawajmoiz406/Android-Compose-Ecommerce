package com.example.myapplication.models.response.category

import java.io.Serializable

data class Category(
    val slug: String,
    val name: String,
    var icon: String = ""
) : Serializable
