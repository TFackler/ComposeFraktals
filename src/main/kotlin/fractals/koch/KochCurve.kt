package fractals.koch

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import components.FraktalCanvas

@Composable
fun ColumnScope.KochCurveCanvas() {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val canvasCenter = Offset(size.width / 2f, size.height / 2f)
        drawRect(
            color = canvasForeground,
            topLeft = canvasCenter - Offset(100f, 100f),
            size = Size(200f, 200f),
            style = Fill,
        )
    }
}