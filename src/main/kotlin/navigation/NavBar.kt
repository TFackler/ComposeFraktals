package navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavBar() {
    val (_, showScreen) = NavigationController.screen
    Row {
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = { showScreen(NavigationController.Screen.KOCH) },
        ) {
            Text("Koch")
        }
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = { showScreen(NavigationController.Screen.SIERPINSKI) },
        ) {
            Text("Sierpinski")
        }
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = { showScreen(NavigationController.Screen.ANT) },
        ) {
            Text("Ant")
        }
    }
}