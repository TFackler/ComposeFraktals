package fractals.ants.model

import androidx.compose.ui.graphics.Color
import fractals.ants.model.Orientation.*

class Ant(
    var x: Int,
    var y: Int,
    var orientation: Orientation,
    val color: Color,
) {
    fun rotateCW() {
        orientation = when (orientation) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun rotateCCW() {
        orientation = when (orientation) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }

    /**
     * Moves the ant my one in the direction it is currently facing.
     */
    fun move() {
        when (orientation) {
            NORTH -> y--
            EAST -> x++
            SOUTH -> y++
            WEST -> x--
        }
    }

    fun copy(): Ant {
        return Ant(x, y, orientation, color.copy())
    }
}