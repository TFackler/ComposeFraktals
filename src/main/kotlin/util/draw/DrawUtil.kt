package util.draw

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Returns the rotated offset.
 */
fun Offset.rotate(degree: Float): Offset {
    val radDegree = (degree / 360.0) * 2 * PI
    val length = getDistance()
    val normX = x / length
    val normY = y / length
    val newX = (normX * cos(radDegree) - (normY * sin(radDegree))).toFloat()
    val newY = (normX * sin(radDegree) + (normY * cos(radDegree))).toFloat()
    return Offset(newX, newY) * length
}