package fractals.koch

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import util.draw.normalize
import util.draw.rotate
import util.draw.sameSidedTriangleHeight

fun DrawScope.drawKochCurve(
    start: Offset,
    end: Offset,
    iterationDepth: Int,
    foregroundColor: Color,
    angle: Int,
) {
    val vertices = mutableListOf(start, end)
    for (iteration in 0 until iterationDepth) {
        var i = 0
        while (i < vertices.size - 1) {
            val a = vertices[i]
            val b = vertices[i + 1]
            val segmentVector = (b - a) / 3f

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