package com.example.myapplication.models.helper

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class SearchController(initialValue: String = "") {
    val str = mutableStateOf(initialValue)
}