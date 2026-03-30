package com.ecommerce.shoppy.config.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ComposableUtils {
    fun DrawScope.topShadowScope(
        shadowColor: Color = Color.Black.copy(alpha = 0.25f),
        topStartCorner: Dp = 0.dp,
        topEndCorner: Dp = 0.dp
    ) {
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.TRANSPARENT
            setShadowLayer(
                4.dp.toPx(),
                0f,
                (-1).dp.toPx(),
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

    fun Modifier.dottedBorder(
        color: Color,
        shape: Shape = RectangleShape,
        dotRadius: Float = 6f,
        gap: Float = 4f,
        strokeWidth: Float = 2f,
    ) = this.drawBehind {
        val stroke = Stroke(
            width = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dotRadius, gap),
                0f
            )
        )
        drawRoundRect(
            color = color,
            style = stroke,
            cornerRadius = CornerRadius(
                if (shape is RoundedCornerShape) 20f else 0f
            )
        )
    }
}