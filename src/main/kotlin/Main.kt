import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fractals.ants.LangtonsAnt
import fractals.koch.Koch
import fractals.sierpinski.Sierpinski
import navigation.NavBar
import navigation.NavigationController

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column {
            NavBar()
            val navigationController = NavigationController
            when (navigationController.screen.value) {
                NavigationController.Screen.KOCH -> Koch()
                NavigationController.Screen.SIERPINSKI -> Sierpinski()
                NavigationController.Screen.ANT -> LangtonsAnt()
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "FraKTals") {
        App()
    }
}