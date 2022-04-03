package fractals.pythagoras

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import components.FraktalCanvas
import components.LabeledSlider

@Composable
fun PythagorasTree() {
    var iterations by remember { mutableStateOf(1f) }

    Column {
        PythagorasTreeCanvas(iterations.toInt())
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            LabeledSlider(
                label = "Iterations",
                value = iterations,
                onValueChange = { iterations = it },
                valueRange = 0f..20f,
            )
        }
    }
}

@Composable
private fun ColumnScope.PythagorasTreeCanvas(iterationDepth: Int) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground,
    ) {
        drawRect(canvasForeground, center, Size(10f * iterationDepth, 10f))
    }
}