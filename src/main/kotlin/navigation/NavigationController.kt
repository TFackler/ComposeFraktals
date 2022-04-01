package navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object NavigationController {
    var screen: MutableState<Screen> = mutableStateOf(Screen.KOCH)

    enum class Screen {
        KOCH, SIERPINSKI, ANT
    }
}