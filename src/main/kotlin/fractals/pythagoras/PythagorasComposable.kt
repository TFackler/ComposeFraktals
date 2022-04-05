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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import components.FraktalCanvas
import components.LabeledSlider
import components.ResettableLabeledSlider
import util.draw.normalize
import util.draw.rotate

@Composable
fun PythagorasTree() {
    var iterations by remember { mutableStateOf(1f) }
    var rectSegmentRatio by remember { mutableStateOf(1f) }
    var treeBaseWidth by remember { mutableStateOf(20f) }

    Column {
        PythagorasTreeCanvas(iterations.toInt(), treeBaseWidth, rectSegmentRatio)
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
            ResettableLabeledSlider(
                label = "Stem segment length",
                value = rectSegmentRatio,
                defaultValue = 1f,
                onValueChange = { rectSegmentRatio = it },
                valueRange = 0.1f..3f,
            )
            ResettableLabeledSlider(
                label = "Tree width",
                value = treeBaseWidth,
                defaultValue = 20f,
                onValueChange = { treeBaseWidth = it },
                valueRange = 10f..500f,
            )
        }
    }
}

@Composable
private fun ColumnScope.PythagorasTreeCanvas(iterationDepth: Int, treeBaseWidth: Float, rectSegmentRatio: Float) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground,
    ) {
        val stemSize = Size(treeBaseWidth, treeBaseWidth * rectSegmentRatio)
        val left = Offset(center.x - (treeBaseWidth / 2f), size.height - 10f)
        val right = left + Offset(stemSize.width, 0f)
        drawLine(color = canvasForeground, start = left, end = right, strokeWidth = 1f)
        drawRectElement(left, right, rectSegmentRatio, canvasForeground, iterationDepth)
    }
}

private fun DrawScope.drawRectElement(
    bottomLeft: Offset,
    bottomRight: Offset,
    rectSegmentRatio: Float,
    color: Color,
    iterationDepth: Int
) {
    if (iterationDepth <= 0) {
        return
    }
    val leftToRight = bottomRight - bottomLeft
    val sideLength = leftToRight.getDistance()
    val bottomToTop = leftToRight.rotate(-90f).normalize() * sideLength * rectSegmentRatio
    val topLeft = bottomLeft + bottomToTop
    val topRight = bottomRight + bottomToTop
    val path = Path()
    path.moveTo(bottomLeft.x, bottomLeft.y)
    path.lineTo(topLeft.x, topLeft.y)
    path.lineTo(topRight.x, topRight.y)
    path.lineTo(bottomRight.x, bottomRight.y)
    drawPath(path = path, color = color, style = Stroke(1f))
    drawTriElement(topLeft, topRight, rectSegmentRatio, color, iterationDepth - 1)
}

private fun DrawScope.drawTriElement(
    left: Offset,
    right: Offset,
    segmentLength: Float,
    color: Color,
    iterationDepth: Int
) {
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
    drawRectElement(left, trianglePeak, segmentLength, color, iterationDepth - 1)
    drawRectElement(trianglePeak, right, segmentLength, color, iterationDepth - 1)
}