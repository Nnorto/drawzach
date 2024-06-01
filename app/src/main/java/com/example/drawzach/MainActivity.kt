package com.example.drawzach

import PathData
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import com.example.drawzach.ui.theme.DrawzachTheme
import android.util.Log

import androidx.compose.foundation.gestures.detectDragGestures

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        db = AppDatabase.getDatabase(this)
        setContent {

            val pathData = remember {
                mutableStateOf(PathData())
            }
            val pathList = remember {
                mutableStateListOf<PathData>()
            }
            DrawzachTheme {
                MainScreen(pathData, pathList)
            }
        }
    }
//    private fun saveDrawing(pathList: List<PathData>, name: String) {
//        lifecycleScope.launch {
//            val bitmap = createBitmapFromPaths(pathList)
//            val stream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
//            val drawing = Drawing(name = name, data = byteArray)
//            db.drawingDao().insert(drawing)
//        }
//    }

//    private fun createBitmapFromPaths(pathList: List<PathData>): Bitmap {
//        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
//        val canvas = android.graphics.Canvas(bitmap)
//        pathList.forEach { pathData ->
//            val paint = Paint().apply {
//                color = pathData.color.toArgb()
//                strokeWidth = pathData.lineWidth
//            }
//            canvas.drawPath(pathData.path.asAndroidPath(), paint)
//        }
//        return bitmap
//    }
}

@Composable
fun DrawCanvas(pathData: MutableState<PathData>, pathList: MutableList<PathData>) {
    var tempPath = androidx.compose.ui.graphics.Path()
    val pathList = remember {
        mutableStateListOf(PathData())
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        tempPath = androidx.compose.ui.graphics.Path()
                    },
                    onDragEnd = {
                        pathList.add(
                            pathData.value.copy(
                                path = tempPath
                            )
                        )
                    }
                ) { change, dragAmount ->
                    tempPath.moveTo(
                        change.position.x - dragAmount.x,
                        change.position.y - dragAmount.y
                    )
                    tempPath.lineTo(
                        change.position.x,
                        change.position.y
                    )

                    if(pathList.size > 0){
                        pathList.removeAt(pathList.size - 1)
                    }
                    pathList.add(
                        pathData.value.copy(
                            path = tempPath
                        )
                    )
                }
            }
    ){
        pathList.forEach { pathData ->
            drawPath(
                pathData.path,
                color = pathData.color,
                style = Stroke(
                    pathData.lineWidth,
                    cap = StrokeCap.Square
                )
            )
        }
        Log.d("MyLog", "Size: ${pathList.size}")
    }
}


@Composable

fun MainScreen(pathData: MutableState<PathData>, pathList: MutableList<PathData>) {
    Column {
        DrawCanvas(pathData, pathList)
        BottomPanel(
            onColorChanged = { color ->
                pathData.value = pathData.value.copy(color = color)
            },
            onLineWidthChanged = { lineWidth ->
                pathData.value = pathData.value.copy(lineWidth = lineWidth)
            },
            onLineOpacityChanged = { opacity ->
                pathData.value = pathData.value.copy(color = pathData.value.color.copy(alpha = opacity / 100f))
            },
            onUndo = {
                if (pathList.isNotEmpty()) {
                    pathList.removeAt(pathList.size - 1)
                }
            }
        )
    }
}
