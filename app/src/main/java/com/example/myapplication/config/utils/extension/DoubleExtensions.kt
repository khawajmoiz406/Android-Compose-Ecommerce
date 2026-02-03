package com.example.myapplication.config.utils.extension

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.cmToInches(decimalPlaces: Int = 2): Double {
    val inches = this / 2.54
    val multiplier = 10.0.pow(decimalPlaces)
    return (inches * multiplier).roundToInt() / multiplier
}