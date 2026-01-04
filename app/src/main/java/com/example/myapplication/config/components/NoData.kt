package com.example.myapplication.config.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NoData(message: String, scrollable: Boolean = true, imageAsset: String = "") {
    val modifier = Modifier.fillMaxSize()
    if (scrollable) modifier.verticalScroll(rememberScrollState())

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = { Text(text = message, textAlign = TextAlign.Center) }
    )
}