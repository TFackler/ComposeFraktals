package fractals.koch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.LabeledSlider
import components.RadioButtonGroup
import fractals.koch.KochComposable.CURVE
import fractals.koch.KochComposable.SNOWFLAKE

object KochComposable {
    const val SNOWFLAKE = "snowflake"
    const val CURVE = "curve"
}

@Composable
fun Koch() {
    var iterations by remember { mutableStateOf(1f) }
    var kochVariant by remember { mutableStateOf(CURVE) }

    Column {
        when (kochVariant) {
            CURVE -> KochCurveCanvas()
            SNOWFLAKE -> KochSnowflakeCanvas()
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
            RadioButtonGroup(
                listOf(CURVE, SNOWFLAKE),
                buildLabel = { it },
                onSelection = { kochVariant = it }
            )
        }
    }
}