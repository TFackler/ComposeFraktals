package fractals.pythagoras

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import components.FraktalCanvas
import components.LabeledSlider
import util.draw.rotate

@Composable
fun PythagorasTree() {
    var iterations by remember { mutableStateOf(1f) }

    Column {
        PythagorasTreeCanvas(iterations.toInt())
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            LabeledSlider(
                label = "Iterations",
                value = iterations,
                onValueChange = { iterations = it },
                valueRange = 0f..25f,
            )
        }
    }
}

@Composable
private fun ColumnScope.PythagorasTreeCanvas(iterationDepth: Int) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground,
    ) {
        val stemSize = 20f
        val left = Offset(center.x - stemSize, size.height - 10f - stemSize)
        val right = left + Offset(stemSize, 0f)
        drawLine(color = canvasForeground, start = left, end = right, strokeWidth = 1f)
        drawRectElement(left, right, canvasForeground, iterationDepth)
    }
}

private fun DrawScope.drawRectElement(bottomLeft: Offset, bottomRight: Offset, color: Color, iterationDepth: Int) {
    if (iterationDepth <= 0) {
        return
    }
    val leftToRight = bottomRight - bottomLeft
    val topLeft = bottomLeft + leftToRight.rotate(-90f)
    val topRight = bottomRight + leftToRight.rotate(-90f)
    val path = Path()
    path.moveTo(bottomLeft.x, bottomLeft.y)
    path.lineTo(topLeft.x, topLeft.y)
    path.lineTo(topRight.x, topRight.y)
    path.lineTo(bottomRight.x, bottomRight.y)
    drawPath(path = path, color = color, style = Stroke(1f))
    drawTriElement(topLeft, topRight, color, iterationDepth - 1)
}

private fun DrawScope.drawTriElement(left: Offset, right: Offset, color: Color, iterationDepth: Int) {
    if (iterationDepth <= 0) {
        return
    }
    val leftToRight = right - left
    val center = left + (leftToRight / 2f)
    val trianglePeak = center + (leftToRight / 2f).rotate(-90f)
    val path = Path()
    path.moveTo(left.x, left.y)
    path.lineTo(trianglePeak.x, trianglePeak.y)
    path.lineTo(right.x, right.y)
    drawPath(path = path, color = color, style = Stroke(1f))
    drawRectElement(left, trianglePeak, color, iterationDepth - 1)
    drawRectElement(trianglePeak, right, color, iterationDepth - 1)
}