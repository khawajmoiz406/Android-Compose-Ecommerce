package com.example.myapplication.models.response.product

import java.io.Serializable

data class Dimensions(
    val depth: Double?,
    val height: Double?,
    val width: Double?
) : Serializable