package com.example.myapplication.utils.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun <T> rememberDebounce(
    value: T,
    delayMillis: Long = 500L,
    onDebounce: (T) -> Unit
) {
    LaunchedEffect(value) {
        delay(delayMillis)
        onDebounce(value)
    }
}