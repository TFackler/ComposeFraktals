package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> RadioButtonGroup(options: List<T>, buildLabel: (T) -> String, onSelection: ((T) -> Unit)? = null) {
    if (options.isEmpty()) {
        return
    }
    var selectedIndex by remember { mutableStateOf(0) }
    Column {
        for (i in options.indices) {
            Row {
                Column {
                    RadioButton(
                        selected = i == selectedIndex,
                        colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary),
                        onClick = {
                            selectedIndex = i
                            if (onSelection != null) {
                                onSelection(options[i])
                            }
                        },
                    )
                }
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(buildLabel(options[i]))
                }
            }
        }
    }
    if (onSelection != null) {
        onSelection(options[selectedIndex])
    }
}