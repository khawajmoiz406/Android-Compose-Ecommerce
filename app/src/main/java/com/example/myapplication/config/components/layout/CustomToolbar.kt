package com.example.myapplication.config.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CustomToolbar(title: String, showBackButton: Boolean = true) {
    val navScope = LocalParentNavController.current

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(75.sdp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 25.sdp, bottom = 10.sdp, end = 10.sdp, start = 10.sdp)
    ) {
        if (showBackButton)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceDim)
                    .size(32.sdp)
                    .clickable { navScope?.popBackStack() },
            ) {
                SvgImage(
                    asset = "back",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.sdp)
                )
            }

        Text(
            text = title,
            fontSize = 16.ssp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun PreviewCustomToolbar() {
    CustomToolbar("Testing")
}