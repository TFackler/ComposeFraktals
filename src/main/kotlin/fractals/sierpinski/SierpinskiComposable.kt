package fractals.sierpinski

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.LabeledSlider
import components.RadioButtonGroup
import util.draw.DrawMode

object Sierpinski {
    const val MIN_DRAW_SIZE = 2.0f
}

@Composable
fun Sierpinski() {
    var iterations by remember { mutableStateOf(1f) }
    var sierpinskiMode by remember { mutableStateOf(SierpinskiMode.TRIANGLE) }
    var drawMode by remember { mutableStateOf(DrawMode.OUTLINE) }

    Column {
        when (sierpinskiMode) {
            SierpinskiMode.TRIANGLE -> SierpinskiTriangleCanvas(iterations.toInt(), drawMode)
            SierpinskiMode.CARPET -> SierpinskiCarpetCanvas(iterations.toInt(), drawMode)
        }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            LabeledSlider(
                label = "Iterations",
                value = iterations,
                onValueChange = { iterations = it },
                valueRange = 0f..15f,
            )
            Row {
                RadioButtonGroup(
                    SierpinskiMode.values().toList(),
                    buildLabel = { it.label },
                    onSelection = { sierpinskiMode = it }
                )
                Spacer(modifier = Modifier.padding(end = 30.dp))
                RadioButtonGroup(
                    DrawMode.values().toList(),
                    buildLabel = { it.label },
                    onSelection = { drawMode = it }
                )
            }
        }
    }
}