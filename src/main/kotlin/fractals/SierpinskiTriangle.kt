package fractals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import components.LabeledSlider
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SierpinskiTriangle() {
    var iterations by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.padding(20.dp).border(2.dp, Color.Green, RectangleShape)
    ) {
        SierpinskiTriangleCanvas(iterations.toInt())
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

@Composable
private fun ColumnScope.SierpinskiTriangleCanvas(iterations: Int? = null) {
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


        val canvasCenter = Offset(size.width / 2f, size.height / 2f)
        val triSize = if (sameSidedTriangleHeight(size.width) < size.height) {
            Size(size.width, sameSidedTriangleHeight(size.width))
        } else {
            Size(sameSidedTriangleWidth(size.height), size.height)
        }

        drawSierpinskiTri(
            Offset(canvasCenter.x - triSize.width / 2f, canvasCenter.y + triSize.height / 2f),
            triSize.width,
            iterations,
            foregroundColor = canvasForeground
        )
    }
}

private fun sameSidedTriangleWidth(height: Float): Float {
    // sqrt(4.0f / 3.0f * height.pow(2))
    return 1.1547005f * height
}

private fun DrawScope.drawTri(bottomLeft: Offset, size: Float, color: Color) {
    val path = Path()
    path.moveTo(bottomLeft.x, bottomLeft.y)
    path.relativeLineTo(size, 0f)
    val height = sameSidedTriangleHeight(size)
    path.relativeLineTo(-size / 2, -height)
    path.close()
    drawPath(path = path, color = color, style = Stroke(width = 1.0f))
}

private fun DrawScope.drawSierpinskiTri(
    bottomLeft: Offset,
    size: Float,
    iterationDepth: Int? = null,
    foregroundColor: Color
) {
    if ((iterationDepth != null && iterationDepth <= 0) || size < 2.0f) {
        return
    }
    drawTri(bottomLeft, size, foregroundColor)

    val newSize = size / 2.0f
    drawSierpinskiTri(bottomLeft, newSize, iterationDepth?.minus(1), foregroundColor)

    val bottomCenter = Offset(bottomLeft.x + newSize, bottomLeft.y)
    drawSierpinskiTri(bottomCenter, newSize, iterationDepth?.minus(1), foregroundColor)

    val height = sameSidedTriangleHeight(size) / 2.0f
    val upperTriBottomLeft = Offset(bottomLeft.x + newSize / 2.0f, bottomLeft.y - height)
    drawSierpinskiTri(upperTriBottomLeft, newSize, iterationDepth?.minus(1), foregroundColor)
}

private fun sameSidedTriangleHeight(size: Float) = sqrt(3f * size.pow(2) / 4f)