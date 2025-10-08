package com.example.myapplication.models.response.product

import com.example.myapplication.base.BaseResponse

data class ProductsResponse(
    val products: List<Product>?
) : BaseResponse()
