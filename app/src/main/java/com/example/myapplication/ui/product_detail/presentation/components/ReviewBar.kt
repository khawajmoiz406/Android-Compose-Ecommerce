package com.example.myapplication.ui.product_detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.config.theme.Yellow
import ir.kaaveh.sdpcompose.sdp
import kotlin.math.roundToInt

@Composable
fun ReviewBar(rating: Float, maxRating: Int = 5, starSize: Dp = 15.sdp, spacing: Dp = 3.sdp) {
    val roundedRating = (rating * 2).roundToInt() / 2f

    Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
        for (i in 1..maxRating) {
            when {
                roundedRating >= i -> {
                    SvgImage(
                        asset = "star_filled",
                        color = Yellow,
                        modifier = Modifier.size(starSize)
                    )
                }

                roundedRating >= i - 0.5f -> {
                    HalfStar(starSize = starSize)
                }

                else -> {
                    SvgImage(
                        asset = "star_filled",
                        modifier = Modifier.size(starSize),
                        color = MaterialTheme.colorScheme.surfaceDim
                    )
                }
            }
        }
    }
}

@Composable
fun HalfStar(starSize: Dp = 32.sdp) {
    Box(modifier = Modifier.size(starSize)) {
        // Background star (unfilled)
        SvgImage(
            asset = "star_filled",
            modifier = Modifier.matchParentSize(),
            color = MaterialTheme.colorScheme.surfaceDim
        )

        // Half Star
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    clipRect(
                        left = 0f,
                        top = 0f,
                        right = size.width / 2f,
                        bottom = size.height
                    ) { this@drawWithContent.drawContent() }
                }
        ) {
            SvgImage(
                asset = "star_filled",
                modifier = Modifier.matchParentSize(),
                color = Yellow
            )
        }
    }
}