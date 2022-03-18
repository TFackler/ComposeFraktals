package util.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @return the height of a same sided triangle given its side length
 */
fun sameSidedTriangleHeight(size: Float) = sqrt(3f * size.pow(2) / 4f)

fun DrawScope.drawTri(a: Offset, b: Offset, c: Offset, color: Color) {
    val path = Path()
    path.moveTo(a.x, a.y)
    path.lineTo(b.x, b.y)
    path.lineTo(c.x, c.y)
    path.close()
    drawPath(path, color, style = Stroke(1.0f))
}

fun DrawScope.fillTri(a: Offset, b: Offset, c: Offset, color: Color) {
    val path = Path()
    path.moveTo(a.x, a.y)
    path.lineTo(b.x, b.y)
    path.lineTo(c.x, c.y)
    path.close()
    drawPath(path, color, style = Fill)
}

fun createSameSidedTri(bottomLeft: Offset, sideLength: Float): Triple<Offset, Offset, Offset> {
    val bottomRight = bottomLeft + Offset(sideLength, 0f)
    val top = bottomLeft + Offset(sideLength / 2f, -sameSidedTriangleHeight(sideLength))
    return Triple(bottomLeft, bottomRight, top)
}

/**
 * @return the width of a same sided triangle given its height
 */
fun sameSidedTriangleWidth(height: Float): Float {
    // sqrt(4.0f / 3.0f * height.pow(2))
    return 1.1547005f * height
}