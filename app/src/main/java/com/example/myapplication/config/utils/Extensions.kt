package com.example.myapplication.config.utils

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.cmToInches(decimalPlaces: Int = 2): Double {
    val inches = this / 2.54
    val multiplier = 10.0.pow(decimalPlaces)
    return (inches * multiplier).roundToInt() / multiplier
}

fun Int.ozToGrams(): Int {
    val grams = this * 28.3495
    return grams.toInt()
}