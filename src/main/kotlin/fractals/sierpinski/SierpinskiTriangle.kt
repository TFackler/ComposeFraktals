package fractals.sierpinski

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import components.FraktalCanvas
import util.draw.*

/**
 * Composable for displaying a sierpinski triangle.
 */
@Composable
fun ColumnScope.SierpinskiTriangleCanvas(iterations: Int? = null, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground,
    ) {
        val canvasCenter = Offset(size.width / 2f, size.height / 2f)
        val triSize = if (sameSidedTriangleHeight(size.width) < size.height) {
            Size(size.width, sameSidedTriangleHeight(size.width))
        } else {
            Size(sameSidedTriangleWidth(size.height), size.height)
        }

        drawSierpinskiTri(
            Offset(canvasCenter.x - triSize.width / 2f, canvasCenter.y + triSize.height / 2f),
            triSize.width,
            iterations,
            foregroundColor = canvasForeground,
            backgroundColor = canvasBackground,
            filled = drawMode == DrawMode.FILL,
        )
    }
}

private fun DrawScope.drawSierpinskiTri(
    bottomLeft: Offset,
    size: Float,
    iterationDepth: Int? = null,
    foregroundColor: Color,
    backgroundColor: Color,
    filled: Boolean,
) {
    if (iterationDepth != null && iterationDepth <= 0) {
        return
    }
    val triangle = createSameSidedTri(bottomLeft, size)
    if (filled) {
        fillTri(triangle.first, triangle.second, triangle.third, foregroundColor)
    } else {
        drawTri(triangle.first, triangle.second, triangle.third, foregroundColor)
    }
    drawSierpinskiTriRecursive(
        triangle.first,
        triangle.second,
        triangle.third,
        iterationDepth?.minus(1),
        foregroundColor,
        backgroundColor,
        filled
    )
}

private fun DrawScope.drawSierpinskiTriRecursive(
    bottomLeft: Offset,
    bottomRight: Offset,
    top: Offset,
    iterationDepth: Int?,
    foregroundColor: Color,
    backgroundColor: Color,
    filled: Boolean,
) {
    if ((iterationDepth != null && iterationDepth <= 0) || (bottomLeft - bottomRight).getDistanceSquared() < Sierpinski.MIN_DRAW_SIZE * Sierpinski.MIN_DRAW_SIZE) {
        return
    }
    // inner upside down triangle
    val bottom = bottomLeft + ((bottomRight - bottomLeft) / 2f)
    val topLeft = bottomLeft + ((top - bottomLeft) / 2f)
    val topRight = bottomRight + ((top - bottomRight) / 2f)
    if (filled) {
        fillTri(bottom, topLeft, topRight, backgroundColor)
    } else {
        drawTri(bottom, topLeft, topRight, foregroundColor)
    }

    // recursive calls for the next 3 triangles
    drawSierpinskiTriRecursive(
        bottomLeft,
        topLeft,
        bottom,
        iterationDepth?.minus(1),
        foregroundColor,
        backgroundColor,
        filled
    )
    drawSierpinskiTriRecursive(
        bottom,
        topRight,
        bottomRight,
        iterationDepth?.minus(1),
        foregroundColor,
        backgroundColor,
        filled
    )
    drawSierpinskiTriRecursive(
        topRight,
        topLeft,
        top,
        iterationDepth?.minus(1),
        foregroundColor,
        backgroundColor,
        filled
    )
}