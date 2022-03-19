package fractals.koch

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import components.FraktalCanvas
import util.draw.rotate
import util.draw.sameSidedTriangleHeight
import util.draw.sameSidedTriangleWidth

@Composable
fun ColumnScope.KochCurveCanvas(iterationDepth: Int) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val curveLength = if (sameSidedTriangleHeight(size.width / 3f) < size.height) {
            size.width
        } else {
            sameSidedTriangleWidth(size.height)
        }

        drawKochCurve(
            start = Offset(size.width / 2f - curveLength / 2f, size.height),
            end = Offset(size.width / 2f + curveLength / 2f, size.height),
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
        )
    }
}

private fun DrawScope.drawKochCurve(
    start: Offset,
    end: Offset,
    iterationDepth: Int,
    foregroundColor: Color,
) {
    val vertices = mutableListOf(start, end)
    for (iteration in 0 until iterationDepth) {
        var i = 0
        while (i < vertices.size - 1) {
            val a = vertices[i]
            val b = vertices[i + 1]
            val newVector = (b - a) / 3f

            val c = a + newVector
            val e = a + (newVector * 2f)
            // -60 degrees because of draw space y increasing downwards
            val d = c + newVector.rotate(-60f)

            vertices.add(i + 1, e)
            vertices.add(i + 1, d)
            vertices.add(i + 1, c)

            i += 1 + 3
        }
    }

    val path = Path()
    path.moveTo(vertices[0].x, vertices[0].y)
    for (i in 1 until vertices.size) {
        path.lineTo(vertices[i].x, vertices[i].y)
    }
    drawPath(
        path,
        foregroundColor,
        style = Stroke(1f),
    )
}