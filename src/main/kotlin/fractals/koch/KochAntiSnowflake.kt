package fractals.koch

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import components.FraktalCanvas
import util.draw.DrawMode
import util.draw.sameSidedTriangleHeight
import util.draw.sameSidedTriangleWidth

@Composable
fun ColumnScope.KochAntiSnowflakeCanvas(iterationDepth: Int, angle: Int, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val triSize = if (sameSidedTriangleHeight(size.width) < size.height) {
            Size(size.width, sameSidedTriangleHeight(size.width))
        } else {
            Size(sameSidedTriangleWidth(size.height), size.height)
        }

        val top = center + Offset(0f, -(triSize.height / 2f))
        val bottomRight = center + Offset(triSize.width / 2f, triSize.height / 2f)
        val bottomLeft = center + Offset(-(triSize.width / 2f), triSize.height / 2f)

        drawAntiKochSnowflake(
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

fun DrawScope.drawAntiKochSnowflake(
    top: Offset,
    bottomLeft: Offset,
    bottomRight: Offset,
    iterationDepth: Int,
    angle: Int,
    foregroundColor: Color,
    drawMode: DrawMode,
) {
    val vertices = mutableListOf<Offset>()
    vertices.addAll(calculateKochCurveVertices(top, bottomRight, iterationDepth, 180 + angle))
    vertices.addAll(calculateKochCurveVertices(bottomRight, bottomLeft, iterationDepth, 180 + angle))
    vertices.addAll(calculateKochCurveVertices(bottomLeft, top, iterationDepth, 180 + angle))
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