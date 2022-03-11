// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
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
    var maxIterations by remember { mutableStateOf(0) }
    var iterations by remember { mutableStateOf(maxIterations) }
    var drawMode by remember { mutableStateOf(DrawMode.SINGLE) }

    fun updateState(newIterations: Int, drawMode: DrawMode) {
        maxIterations = newIterations
        when (drawMode) {
            DrawMode.SINGLE -> {
                // stop coroutine
                iterations = maxIterations
            }
            DrawMode.ANIMATE -> {
                // stop coroutine
                // start coroutine
            }
        }
    }

    Row(
        modifier = Modifier.padding(20.dp).border(2.dp, Color.Green, RectangleShape)
    ) {
        val canvasBackground = MaterialTheme.colors.primarySurface
        val canvasForeground = MaterialTheme.colors.onPrimary
        Canvas(
            modifier = Modifier
                .padding(10.dp)
                .background(canvasBackground)
                .padding(10.dp)
                .fillMaxHeight()
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
                iterations
            )
        }
        Column(
            modifier = Modifier.padding(5.dp).border(2.dp, Color.Cyan, RectangleShape).padding(5.dp).fillMaxHeight()
                .width(250.dp)
        ) {
            IntegerTextField(
                "Iterations",
                0,
                100,
                default = maxIterations,
                onValueChange = { updateState(it, drawMode) })
            RadioButtonGroup(
                DrawMode.values().toList(),
                buildLabel = { it.label },
                onSelection = {
                    updateState(maxIterations, it)
                }
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
fun <T> RadioButtonGroup(options: List<T>, buildLabel: (T) -> String, onSelection: ((T) -> Unit)? = null) {
    if (options.isEmpty()) {
        return
    }
    var selectedIndex by remember { mutableStateOf(0) }
    Column {
        for (i in options.indices) {
            Row {
                Column {
                    RadioButton(
                        selected = i == selectedIndex,
                        colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary),
                        onClick = {
                            selectedIndex = i
                            if (onSelection != null) {
                                onSelection(options[i])
                            }
                        },
                    )
                }
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(buildLabel(options[i]))
                }
            }
        }
    }
    if (onSelection != null) {
        onSelection(options[selectedIndex])
    }
}