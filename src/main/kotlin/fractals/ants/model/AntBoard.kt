package fractals.ants.model

import androidx.compose.ui.graphics.Color

class AntBoard(
    val width: Int,
    val height: Int,
) {
    val board = Array(width) { Array(height) { Color(255, 255, 255) } }
    val ants = mutableListOf<Ant>()

    fun get(x: Int, y: Int): Color {
        return board[x][y]
    }

    fun set(x: Int, y: Int, color: Color) {
        board[x][y] = color
    }

    fun set(x: Int, y: Int, red: Int, green: Int, blue: Int) {
        set(x, y, Color(red, green, blue))
    }

    fun copy(): AntBoard {
        val copy = AntBoard(width, height)
        for (x in 0 until width) {
            for (y in 0 until height) {
                copy.set(x, y, this.get(x, y))
            }
        }
        ants.forEach { ant -> copy.ants.add(ant.copy()) }
        return copy
    }
}