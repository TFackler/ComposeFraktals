package fractals.sierpinski

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * Composable for displaying a sierpinski carpet.
 */
@Composable
fun ColumnScope.SierpinskiCarpetCanvas(iterations: Int? = null, drawMode: DrawMode) {
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
        val sideLength = java.lang.Float.min(size.width, size.height)

        drawSierpinskiCarpet(
            Offset(canvasCenter.x - sideLength / 2f, canvasCenter.y - sideLength / 2f),
            sideLength,
            iterations,
            foregroundColor = canvasForeground,
            backgroundColor = canvasBackground,
            filled = drawMode == DrawMode.FILL,
        )
    }
}

private fun DrawScope.drawSierpinskiCarpet(
    topLeft: Offset,
    size: Float,
    iterationDepth: Int? = null,
    foregroundColor: Color,
    backgroundColor: Color,
    filled: Boolean,
) {
    if (iterationDepth != null && iterationDepth <= 0) {
        return
    }
    if (filled) {
        drawRect(
            color = foregroundColor,
            topLeft = topLeft,
            size = Size(size, size),
            style = Fill,
        )
    } else {
        drawRect(
            color = foregroundColor,
            topLeft = topLeft,
            size = Size(size, size),
            style = Stroke(1f),
        )
    }
    drawSierpinskiCarpetRecursive(
        topLeft,
        size,
        iterationDepth?.minus(1),
        foregroundColor,
        backgroundColor,
        filled,
    )
}

private fun DrawScope.drawSierpinskiCarpetRecursive(
    topLeft: Offset,
    size: Float,
    iterationDepth: Int? = null,
    foregroundColor: Color,
    backgroundColor: Color,
    filled: Boolean,
) {
    if ((iterationDepth != null && iterationDepth <= 0) || size <= Sierpinski.MIN_DRAW_SIZE) {
        return
    }
    // inner rectangle
    val innerSize = size / 3f
    val innerTopLeft = topLeft + Offset(innerSize, innerSize)
    if (filled) {
        drawRect(
            backgroundColor,
            innerTopLeft,
            Size(innerSize, innerSize),
            style = Fill,
        )
    } else {
        drawRect(
            foregroundColor,
            innerTopLeft,
            Size(innerSize, innerSize),
            style = Stroke(1.0f),
        )
    }
    // recursive calls for the next 8 squares
    for (i in 0..2) {
        for (j in 0..2) {
            if (!(i == 1 && i == j)) {
                drawSierpinskiCarpetRecursive(
                    topLeft + Offset(innerSize * i, innerSize * j),
                    innerSize,
                    iterationDepth?.minus(1),
                    foregroundColor,
                    backgroundColor,
                    filled,
                )
            }
        }
    }
}