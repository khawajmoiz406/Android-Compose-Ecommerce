package com.example.myapplication.ui.product_detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RoundedText(text: String, textColor: Color, backgroundColor: Color, bold: Boolean = false) {
    return Text(
        text = text,
        fontSize = 10.ssp,
        lineHeight = 10.ssp,
        fontWeight = if (bold) FontWeight.SemiBold else FontWeight.Normal,
        color = textColor,
        modifier = Modifier
            .background(shape = RoundedCornerShape(20.dp), color = backgroundColor)
            .padding(horizontal = 8.sdp, vertical = 3.sdp)
    )
}