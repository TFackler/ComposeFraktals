package fractals.koch

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.LabeledSlider
import components.RadioButtonGroup
import components.ResettableLabeledSlider
import fractals.koch.KochComposable.ANTI_SNOWFLAKE
import fractals.koch.KochComposable.CURVE
import fractals.koch.KochComposable.DEFAULT_ANGLE
import fractals.koch.KochComposable.SNOWFLAKE
import util.draw.DrawMode

object KochComposable {
    const val SNOWFLAKE = "snowflake"
    const val CURVE = "curve"
    const val ANTI_SNOWFLAKE = "anti-snowflake"

    const val DEFAULT_ANGLE = 90f
}

@Composable
fun Koch() {
    var iterations by remember { mutableStateOf(1f) }
    var angle by remember { mutableStateOf(DEFAULT_ANGLE) }
    var kochVariant by remember { mutableStateOf(CURVE) }
    var drawMode by remember { mutableStateOf(DrawMode.OUTLINE) }


    Column {
        when (kochVariant) {
            CURVE -> KochCurveCanvas(iterations.toInt(), angle.toInt(), drawMode)
            SNOWFLAKE -> KochSnowflakeCanvas(iterations.toInt(), angle.toInt(), drawMode)
            ANTI_SNOWFLAKE -> KochAntiSnowflakeCanvas(iterations.toInt(), angle.toInt(), drawMode)
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
            ResettableLabeledSlider(
                label = "Angle",
                value = angle,
                defaultValue = DEFAULT_ANGLE,
                onValueChange = { angle = it },
                valueRange = 0f..180f,
            )
            Row {
                RadioButtonGroup(
                    listOf(CURVE, SNOWFLAKE, ANTI_SNOWFLAKE),
                    buildLabel = { it },
                    onSelection = { kochVariant = it }
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