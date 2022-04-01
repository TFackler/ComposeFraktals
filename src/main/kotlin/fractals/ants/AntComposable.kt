package fractals.ants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
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

    val startingBoard = AntBoard(100, 100)
    startingBoard.ants.add(Ant(50, 50, Orientation.NORTH, Color(0, 0, 0)))
    val simulation = AntSimulation(startingBoard)
    simulation.simulate(steps = maxIterations)

    Column {
        AntCanvas(iterations.toInt(), simulation)
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
        }
    }
}

@Composable
fun ColumnScope.AntCanvas(iteration: Int, antSimulation: AntSimulation) {
    FraktalCanvas(
        backgroundColor = Color.White
    ) {
        drawBoard(antSimulation.steps[iteration])
    }
}

private fun DrawScope.drawBoard(antBoard: AntBoard) {
    for (x in 0 until antBoard.width) {
        for (y in 0 until antBoard.height) {
            val cellColor = antBoard.get(x, y)
            val factor = 5f
            drawRect(
                color = Color(
                    red = cellColor.red,
                    green = cellColor.green,
                    blue = cellColor.blue
                ),
                topLeft = Offset(x.toFloat() * factor, y.toFloat() * factor),
                size = Size(factor, factor),
                style = Fill,
            )
        }
    }
}