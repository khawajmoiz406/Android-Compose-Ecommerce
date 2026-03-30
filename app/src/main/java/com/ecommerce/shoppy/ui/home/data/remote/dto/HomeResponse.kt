package com.ecommerce.shoppy.ui.home.data.remote.dto

import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.Product
import java.io.Serializable

data class HomeResponse(
    var products: List<Product>?,
    var categories: List<Category>?
) : Serializable