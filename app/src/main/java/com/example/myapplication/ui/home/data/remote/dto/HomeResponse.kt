package com.example.myapplication.ui.home.data.remote.dto

import com.example.myapplication.core.model.Category
import com.example.myapplication.core.model.Product
import java.io.Serializable

data class HomeResponse(
    var products: List<Product>?,
    var categories: List<Category>?
) : Serializable