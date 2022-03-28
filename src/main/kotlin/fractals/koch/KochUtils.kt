package fractals.koch

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import fractals.koch.KochUtils.MIN_DRAWN_TRI_SIZE
import util.draw.DrawMode
import util.draw.DrawMode.FILL
import util.draw.DrawMode.OUTLINE
import util.draw.normalize
import util.draw.rotate
import util.draw.sameSidedTriangleHeight

object KochUtils {
    const val MIN_DRAWN_TRI_SIZE = .33f
}

fun DrawScope.drawKochCurve(
    start: Offset,
    end: Offset,
    iterationDepth: Int,
    foregroundColor: Color,
    angle: Int,
    drawMode: DrawMode,
) {
    val path = calculateKochCurveVertices(start, end, iterationDepth, angle).toPath()
    when (drawMode) {
        OUTLINE -> drawPath(
            path,
            foregroundColor,
            style = Stroke(1f),
        )
        FILL -> {
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
            OUTLINE -> Stroke(1f)
            FILL -> Fill
        }
    )
}

private fun calculateKochCurveVertices(
    start: Offset,
    end: Offset,
    iterationDepth: Int,
    angle: Int,
): List<Offset> {
    val vertices = mutableListOf(start, end)
    iterations@ for (iteration in 0 until iterationDepth) {
        var i = 0
        while (i < vertices.size - 1) {
            val a = vertices[i]
            val b = vertices[i + 1]
            val segmentVector = (b - a) / 3f
            if (segmentVector.getDistance() < MIN_DRAWN_TRI_SIZE) {
                break@iterations
            }

            val c = a + segmentVector
            val center = a + (segmentVector * 1.5f)
            val e = a + (segmentVector * 2f)

            // -angle because of draw space y increasing downwards, 180 - angle to flip angle
            val rotationAngle = -(180 - angle)
            val triHeight = sameSidedTriangleHeight(segmentVector.getDistance())
            val d = center + segmentVector.normalize().rotate(rotationAngle.toFloat()) * triHeight

            vertices.add(i + 1, e)
            vertices.add(i + 1, d)
            vertices.add(i + 1, c)

            i += 1 + 3
        }
    }
    return vertices
}

private fun List<Offset>.toPath(): Path {
    val path = Path()
    path.moveTo(this[0].x, this[0].y)
    for (i in 1 until this.size) {
        path.lineTo(this[i].x, this[i].y)
    }
    return path
}