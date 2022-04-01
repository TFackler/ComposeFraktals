package fractals.ants.model

import androidx.compose.ui.graphics.Color

class AntSimulation(
    startingBoard: AntBoard
) {
    val steps: MutableList<AntBoard> = mutableListOf(startingBoard)

    fun simulate(steps: Int = 1) {
        for (i in 1..steps) {
            simulateStep()
        }
    }

    private fun simulateStep() {
        val nextBoard = steps.last().copy()
        for (ant in nextBoard.ants) {
            if (ant.x in 0 until nextBoard.width && ant.y in 0 until nextBoard.height) {
                if (nextBoard.get(ant.x, ant.y) == Color.White) {
                    ant.rotateCW()
                    nextBoard.set(ant.x, ant.y, ant.color)
                    ant.move()
                } else {
                    ant.rotateCCW()
                    nextBoard.set(ant.x, ant.y, Color.White)
                    ant.move()
                }
            }
        }
        steps.add(nextBoard)
    }
}