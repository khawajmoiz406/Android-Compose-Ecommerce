package com.example.myapplication.models.helper

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.models.request.ProductsRequest

@Stable
class SearchController(initialValue: String = "", productsRequest: ProductsRequest = ProductsRequest()) {
    val str = mutableStateOf(initialValue)
    val filters = mutableStateOf(productsRequest)
}