package fractals.koch

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import components.FraktalCanvas
import components.LabeledSlider
import components.RadioButtonGroup
import components.ResettableLabeledSlider
import fractals.koch.KochComposable.CURVE
import fractals.koch.KochComposable.DEFAULT_ANGLE
import fractals.koch.KochComposable.SNOWFLAKE
import util.draw.*

object KochComposable {
    const val SNOWFLAKE = "snowflake"
    const val CURVE = "curve"

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
                valueRange = 1f..179f,
            )
            Row {
                RadioButtonGroup(
                    listOf(CURVE, SNOWFLAKE),
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

@Composable
private fun ColumnScope.KochCurveCanvas(iterationDepth: Int, angle: Int, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val curveLength = if (sameSidedTriangleHeight(size.width / 3f) < size.height) {
            size.width
        } else {
            sameSidedTriangleWidth(size.height)
        }

        drawKochCurve(
            start = Offset(size.width / 2f - curveLength / 2f, size.height),
            end = Offset(size.width / 2f + curveLength / 2f, size.height),
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
            angle = angle,
            drawMode = drawMode,
        )
    }
}

@Composable
private fun ColumnScope.KochSnowflakeCanvas(iterationDepth: Int, angle: Int, drawMode: DrawMode) {
    val canvasBackground = MaterialTheme.colors.primarySurface
    val canvasForeground = MaterialTheme.colors.onPrimary
    FraktalCanvas(
        backgroundColor = canvasBackground
    ) {
        val surroundingCircleRadius = if (size.width < size.height) {
            size.width / 2f
        } else {
            size.height / 2f
        }

        val centerToTop = Offset(0f, -surroundingCircleRadius)
        val top = center + centerToTop
        val bottomRight = center + centerToTop.rotate(120f)
        val bottomLeft = center + centerToTop.rotate(240f)

        if (drawMode == DrawMode.FILL) {
            fillTri(top, bottomLeft, bottomRight, canvasForeground)
        }
        drawKochCurve(
            start = top,
            end = bottomRight,
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
            angle = angle,
            drawMode = drawMode,
        )
        drawKochCurve(
            start = bottomRight,
            end = bottomLeft,
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
            angle = angle,
            drawMode = drawMode,
        )
        drawKochCurve(
            start = bottomLeft,
            end = top,
            iterationDepth = iterationDepth,
            foregroundColor = canvasForeground,
            angle = angle,
            drawMode = drawMode,
        )
    }
}