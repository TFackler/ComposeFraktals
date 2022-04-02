package fractals.ants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp
import components.FraktalCanvas
import components.LabeledSlider
import fractals.ants.model.Ant
import fractals.ants.model.AntBoard
import fractals.ants.model.AntSimulation
import fractals.ants.model.Orientation

@Composable
fun LangtonsAnt() {
    val maxIterations = 5000
    var iterations by remember { mutableStateOf(1f) }
    var scaleFactor by remember { mutableStateOf(1f) }

    val boardWidth = 100
    val boardHeight = 100
    val startingBoard = AntBoard(boardWidth, boardHeight)
    startingBoard.ants.add(Ant(boardWidth / 2, boardHeight / 2, Orientation.NORTH, MaterialTheme.colors.primary))
    val simulation = AntSimulation(startingBoard)
    simulation.simulate(steps = maxIterations)

    Column {
        AntCanvas(iterations.toInt(), simulation, scaleFactor)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            LabeledSlider(
                label = "Iterations",
                value = iterations,
                onValueChange = { iterations = it },
                valueRange = 0f..maxIterations.toFloat(),
            )
            LabeledSlider(
                label = "Scale",
                value = scaleFactor,
                onValueChange = { scaleFactor = it },
                valueRange = 1f..20f,
            )
        }
    }
}

@Composable
fun ColumnScope.AntCanvas(iteration: Int, antSimulation: AntSimulation, scaleFactor: Float) {
    FraktalCanvas(
        backgroundColor = Color.White
    ) {
        drawBoard(antSimulation.steps[iteration], scaleFactor)
    }
}

private fun DrawScope.drawBoard(antBoard: AntBoard, scaleFactor: Float) {
    inset(
        horizontal = center.x - (antBoard.width / 2) * scaleFactor,
        vertical = center.y - (antBoard.height / 2) * scaleFactor,
    ) {
        for (x in 0 until antBoard.width) {
            for (y in 0 until antBoard.height) {
                val cellColor = antBoard.get(x, y)
                drawRect(
                    color = Color(
                        red = cellColor.red,
                        green = cellColor.green,
                        blue = cellColor.blue
                    ),
                    topLeft = Offset(x.toFloat() * scaleFactor, y.toFloat() * scaleFactor),
                    size = Size(scaleFactor, scaleFactor),
                    style = Fill,
                )
            }
        }
    }
}