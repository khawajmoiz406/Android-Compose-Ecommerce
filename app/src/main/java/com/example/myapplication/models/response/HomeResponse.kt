package com.example.myapplication.models.response

import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.Product
import java.io.Serializable

data class HomeResponse(
    var products: List<Product>?,
    var categories: List<Category>?
) : Serializable
