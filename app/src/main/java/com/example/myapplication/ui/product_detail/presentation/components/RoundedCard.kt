package com.example.myapplication.ui.product_detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.config.components.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RoundedCard(icon: String, heading: String, value: String, foregroundColor: Color, modifier: Modifier = Modifier) {
    return Column(
        modifier = modifier
            .background(foregroundColor.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .border(width = 0.3.dp, color = foregroundColor, shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = icon,
                color = foregroundColor,
                modifier = Modifier.size(12.sdp, 12.sdp)
            )

            Spacer(Modifier.width(5.sdp))

            Text(
                text = heading,
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                color = foregroundColor,
            )
        }

        Spacer(Modifier.height(8.sdp))

        Text(
            text = value,
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = foregroundColor,
        )
    }
}