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
import util.draw.rotate

@Composable
fun ColumnScope.KochSnowflakeCanvas(iterationDepth: Int, angle: Int, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val surroundingCircleRadius = if (size.width < size.height) {
            size.width / 2f
        } else {
            size.height / 2f
        }

        val centerToTop = Offset(0f, -surroundingCircleRadius)
        val top = center + centerToTop
        val bottomRight = center + centerToTop.rotate(120f)
        val bottomLeft = center + centerToTop.rotate(240f)

        drawKochSnowflake(
            top = top,
            bottomLeft = bottomLeft,
            bottomRight = bottomRight,
            iterationDepth = iterationDepth,
            angle = angle,
            foregroundColor = canvasForeground,
            drawMode = drawMode,
        )
    }
}

private fun DrawScope.drawKochSnowflake(
    top: Offset,
    bottomLeft: Offset,
    bottomRight: Offset,
    iterationDepth: Int,
    angle: Int,
    foregroundColor: Color,
    drawMode: DrawMode,
) {
    val vertices = mutableListOf<Offset>()
    vertices.addAll(calculateKochCurveVertices(top, bottomRight, iterationDepth,  angle))
    vertices.addAll(calculateKochCurveVertices(bottomRight, bottomLeft, iterationDepth, angle))
    vertices.addAll(calculateKochCurveVertices(bottomLeft, top, iterationDepth, angle))
    val path = vertices.toPath()

    drawPath(
        path,
        foregroundColor,
        style = when (drawMode) {
            DrawMode.OUTLINE -> Stroke(1f)
            DrawMode.FILL -> Fill
        }
    )
}