package com.example.myapplication.config.utils.extension

fun Int.ozToGrams(): Int {
    val grams = this * 28.3495
    return grams.toInt()
}