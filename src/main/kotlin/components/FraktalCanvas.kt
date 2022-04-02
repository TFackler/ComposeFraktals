package components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.FraktalCanvas(backgroundColor: Color, onDraw: DrawScope.() -> Unit) {
    Canvas(
        modifier = Modifier
            .padding(10.dp)
            .background(backgroundColor)
            .padding(10.dp)
            .fillMaxWidth()
            .weight(1.0f)
            .clipToBounds(),
        onDraw = onDraw,
    )
}