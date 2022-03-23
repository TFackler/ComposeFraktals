package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LabeledSlider(
    label: String? = null,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
        )
    }
}

@Composable
fun ResettableLabeledSlider(
    label: String? = null,
    value: Float,
    defaultValue: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LabeledSlider(label, value, onValueChange, valueRange, modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onValueChange(defaultValue) }
        ) {
            Icon(Icons.Default.Refresh, null)
        }
    }
}