package fractals.koch

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import components.FraktalCanvas
import util.draw.DrawMode
import util.draw.sameSidedTriangleHeight
import util.draw.sameSidedTriangleWidth

@Composable
fun ColumnScope.KochCurveCanvas(iterationDepth: Int, angle: Int, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val curveLength = if (sameSidedTriangleHeight(size.width / 3f) < size.height) {
            size.width
        } else {
            sameSidedTriangleWidth(size.height) * 3f
        }

        drawKochCurve(
            start = Offset(size.width / 2f - curveLength / 2f, size.height),
            end = Offset(size.width / 2f + curveLength / 2f, size.height),
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
            angle = angle,
            drawMode = drawMode,
        )
    }
}

private fun DrawScope.drawKochCurve(
    start: Offset,
    end: Offset,
    iterationDepth: Int,
    foregroundColor: Color,
    angle: Int,
    drawMode: DrawMode,
) {
    val path = calculateKochCurveVertices(start, end, iterationDepth, angle).toPath()
    when (drawMode) {
        DrawMode.OUTLINE -> drawPath(
            path,
            foregroundColor,
            style = Stroke(1f),
        )
        DrawMode.FILL -> {
            drawLine(
                color = foregroundColor,
                start = start,
                end = end,
                strokeWidth = 2f,
            )
            drawPath(
                path,
                foregroundColor,
                style = Fill,
            )
        }
    }
}