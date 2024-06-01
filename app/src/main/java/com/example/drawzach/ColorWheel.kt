package com.example.drawzach

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun ColorWheel(modifier: Modifier = Modifier, onColorChanged: (Color) -> Unit, initialColor: Color) {
    val center = remember { Offset(100f, 150f) } // Это можно настроить в зависимости от размера компонента
    val radius = remember { 180f } // Радиус цветового круга

    Canvas(modifier = modifier.pointerInput(Unit) {
        detectTapGestures { offset ->
            val dx = offset.x - center.x
            val dy = offset.y - center.y
            if (sqrt(dx * dx + dy * dy) <= radius) {
                val angle = atan2(dy, dx)
                val hue = ((angle * 180 / PI).toFloat() + 360) % 360
                val selectedColor = Color.hsv(hue, 1f, 1f)
                onColorChanged(selectedColor)
            }
        }
    }) {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            for (i in 0 until 360 step 2) {
                val angleRad = Math.toRadians(i.toDouble())
                val lineToX = center.x + radius * cos(angleRad)
                val lineToY = center.y + radius * sin(angleRad)
                paint.color = Color.hsv(i.toFloat(), 1f, 1f)
                paint.strokeWidth = 10f
                canvas.drawLine(
                    p1 = center,
                    p2 = Offset(lineToX.toFloat(), lineToY.toFloat()),
                    paint = paint
                )
            }
        }
    }
}
