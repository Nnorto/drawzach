package com.example.drawzach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomPanel(
    onColorChanged: (Color) -> Unit,
    onLineWidthChanged: (Float) -> Unit,
    onLineOpacityChanged: (Float) -> Unit,
    onUndo: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                ColorWheel(
                    modifier = Modifier.size(100.dp), // Увеличим размер цветового круга
                    onColorChanged = onColorChanged,
                    initialColor = Color.Red // Можно установить начальный цвет, который у вас уже используется
                )
            }
        }
        CustomSlider(onLineWidthChanged)
        CustomSlider2(onLineOpacityChanged)
        Button(onClick = onUndo, modifier = Modifier.padding(20.dp)) {
            Text("Undo")
        }
    }
}


@Composable
fun CustomSlider(onLineWidthChange: (Float) -> Unit) {
    var position by remember {
        mutableStateOf(0.05f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("толщина: ${(position * 100).toInt()}")
        Slider(
            value = position,
            onValueChange = {
                val tempPos = if (it > 0) it else 0.01f
                position = tempPos
                onLineWidthChange(tempPos * 100)
            }
        )
    }
}

@Composable
fun CustomSlider2(onLineWidthChange: (Float) -> Unit) {
    var position by remember {
        mutableStateOf(0.05f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("прозрачность: ${(position * 100).toInt()}")
        Slider(
            value = position,
            onValueChange = {
                val tempPos = if (it > 0) it else 0.01f
                position = tempPos
                onLineWidthChange(tempPos * 100)
            }
        )
    }
}
