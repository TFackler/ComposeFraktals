package fractals.koch

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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.KochSnowflakeCanvas() {
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
        drawRect(
            color = canvasForeground,
            topLeft = canvasCenter - Offset(200f, 200f),
            size = Size(400f, 400f),
            style = Fill,
        )
    }
}