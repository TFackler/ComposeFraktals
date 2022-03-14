import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fractals.SierpinskiTriangle

@Composable
@Preview
fun App() {
    MaterialTheme {
        SierpinskiTriangle()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "FraKTals") {
        App()
    }
}