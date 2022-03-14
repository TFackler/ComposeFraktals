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
import components.RadioButtonGroup
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SierpinskiTriangle() {
    var iterations by remember { mutableStateOf(0f) }
    var mode by remember { mutableStateOf(SierpinskiMode.TRIANGLE) }

    Column(
        modifier = Modifier.padding(20.dp).border(2.dp, Color.Green, RectangleShape)
    ) {
        when (mode) {
            SierpinskiMode.TRIANGLE -> SierpinskiTriangleCanvas(iterations.toInt())
            SierpinskiMode.CARPET -> SierpinskiCarpetCanvas(iterations.toInt())
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
            RadioButtonGroup(
                SierpinskiMode.values().toList(),
                buildLabel = { it.label },
                onSelection = { mode = it }
            )
        }
    }
}

@Composable
private fun ColumnScope.SierpinskiCarpetCanvas(iterations: Int? = null) {
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
        drawRect(
            color = canvasForeground,
            topLeft = canvasCenter.minus(Offset(100f, 100f)),
            size = Size(200f, 200f)
        )
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

private fun DrawScope.drawSierpinskiTri(
    bottomLeft: Offset,
    size: Float,
    iterationDepth: Int? = null,
    foregroundColor: Color
) {
    if (iterationDepth != null && iterationDepth <= 0) {
        return
    }
    val triangle = createSameSidedTri(bottomLeft, size)
    drawTri(triangle.first, triangle.second, triangle.third, foregroundColor)
    drawSierpinskiRecursive(triangle.first, triangle.second, triangle.third, iterationDepth?.minus(1), foregroundColor)
}

private fun createSameSidedTri(bottomLeft: Offset, sideLength: Float): Triple<Offset, Offset, Offset> {
    val bottomRight = bottomLeft + Offset(sideLength, 0f)
    val top = bottomLeft + Offset(sideLength / 2f, -sameSidedTriangleHeight(sideLength))
    return Triple(bottomLeft, bottomRight, top)
}

private fun DrawScope.drawSierpinskiRecursive(
    bottomLeft: Offset,
    bottomRight: Offset,
    top: Offset,
    iterationDepth: Int?,
    color: Color
) {
    if (iterationDepth != null && iterationDepth <= 0) {
        return
    }
    if ((bottomLeft - bottomRight).getDistanceSquared() < 4.0f) {
        return
    }
    // inner upside down triangle
    val bottom = bottomLeft + ((bottomRight - bottomLeft) / 2f)
    val topLeft = bottomLeft + ((top - bottomLeft) / 2f)
    val topRight = bottomRight + ((top - bottomRight) / 2f)
    drawTri(bottom, topLeft, topRight, color)

    // recursive calls for the next 3 triangles
    drawSierpinskiRecursive(bottomLeft, topLeft, bottom, iterationDepth?.minus(1), color)
    drawSierpinskiRecursive(bottom, topRight, bottomRight, iterationDepth?.minus(1), color)
    drawSierpinskiRecursive(topRight, topLeft, top, iterationDepth?.minus(1), color)
}

private fun DrawScope.drawTri(a: Offset, b: Offset, c: Offset, color: Color) {
    val path = Path()
    path.moveTo(a.x, a.y)
    path.lineTo(b.x, b.y)
    path.lineTo(c.x, c.y)
    path.close()
    drawPath(path, color, style = Stroke(1.0f))
}

private fun sameSidedTriangleWidth(height: Float): Float {
    // sqrt(4.0f / 3.0f * height.pow(2))
    return 1.1547005f * height
}

private fun sameSidedTriangleHeight(size: Float) = sqrt(3f * size.pow(2) / 4f)