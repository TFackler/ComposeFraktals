// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
@Preview
fun App() {
    MaterialTheme {
        SierpinskiTriangle()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}


@Composable
fun SierpinskiTriangle() {
    var iterations by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.padding(20.dp).border(2.dp, Color.Green, RectangleShape)
    ) {
        val canvasBackground = MaterialTheme.colors.primarySurface
        val canvasForeground = MaterialTheme.colors.onPrimary
        Canvas(
            modifier = Modifier
                .padding(10.dp)
                .background(canvasBackground)
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            fun drawTri(bottomLeft: Offset, size: Float) {
                val path = Path()
                path.moveTo(bottomLeft.x, bottomLeft.y)
                path.relativeLineTo(size, 0f)
                val height = sameSidedTriangleHeight(size)
                path.relativeLineTo(-size / 2, -height)
                path.close()
                drawPath(path = path, color = canvasForeground, style = Stroke(width = 1.0f))
            }

            fun drawSierpinskiTri(bottomLeft: Offset, size: Float, iterationDepth: Int? = null) {
                if ((iterationDepth != null && iterationDepth <= 0) || size < 1.0f) {
                    return
                }
                drawTri(bottomLeft, size)

                val newSize = size / 2.0f
                drawSierpinskiTri(bottomLeft, newSize, iterationDepth?.minus(1))

                val bottomCenter = Offset(bottomLeft.x + newSize, bottomLeft.y)
                drawSierpinskiTri(bottomCenter, newSize, iterationDepth?.minus(1))

                val height = sameSidedTriangleHeight(size) / 2.0f
                val upperTriBottomLeft = Offset(bottomLeft.x + newSize / 2.0f, bottomLeft.y - height)
                drawSierpinskiTri(upperTriBottomLeft, newSize, iterationDepth?.minus(1))
            }

            val canvasCenter = Offset(size.width / 2f, size.height / 2f)
            val triSize = if (sameSidedTriangleHeight(size.width) < size.height) {
                Size(size.width, sameSidedTriangleHeight(size.width))
            } else {
                Size(sameSidedTriangleWidth(size.height), size.height)
            }

            drawSierpinskiTri(
                Offset(canvasCenter.x - triSize.width / 2f, canvasCenter.y + triSize.height / 2f),
                triSize.width,
                iterations.toInt()
            )
        }
        Column(
            modifier = Modifier
                .padding(5.dp)
                .border(2.dp, Color.Cyan, RectangleShape)
                .padding(5.dp)
                .fillMaxWidth()
                .width(250.dp)
        ) {
            LabeledSlider(
                label = "Iterations",
                value = iterations,
                onValueChange = { iterations = it },
                valueRange = 0f..15f,
            )
        }
    }
}

fun sameSidedTriangleHeight(size: Float) = sqrt(3f * size.pow(2) / 4f)

fun sameSidedTriangleWidth(height: Float): Float {
    // sqrt(4.0f / 3.0f * height.pow(2))
    return 1.1547005f * height
}

@Composable
fun LabeledSlider(
    label: String? = null,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (label != null) {
            Text(
                text = label,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
        )
    }
}