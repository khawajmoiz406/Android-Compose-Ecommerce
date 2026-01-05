package com.example.myapplication.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ComposableUtils {
    fun DrawScope.topShadowScope(
        shadowColor: Color = Color.Companion.Black.copy(alpha = 0.25f),
        topStartCorner: Dp = 0.dp,
        topEndCorner: Dp = 0.dp
    ) {
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.TRANSPARENT
            setShadowLayer(
                4.dp.toPx(),   // blur
                0f,            // offset X
                (-1).dp.toPx(),// offset Y
                shadowColor.toArgb()
            )
        }

        drawIntoCanvas {
            it.nativeCanvas.drawRoundRect(
                0f,
                0f,
                size.width,
                size.height / 2,
                topStartCorner.toPx(),
                topEndCorner.toPx(),
                paint
            )
        }
    }
}