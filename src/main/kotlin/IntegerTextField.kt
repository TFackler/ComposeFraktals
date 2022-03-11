import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.lang.NumberFormatException

@Composable
fun IntegerTextField(label: String, lowerBound: Int? = null, upperBound: Int? = null, default: Int? = null, onValueChange: (Int) -> Unit) {
    var isError by remember { mutableStateOf(false) }
    val defaultText = default?.toString() ?: ""
    var text by remember { mutableStateOf(TextFieldValue(defaultText)) }
    var errorMessage by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                val parsed = text.toInt()
                if (parsed == null) {
                    isError = true
                    errorMessage = "not an integer"
                    return@OutlinedTextField
                }
                if (lowerBound != null && lowerBound > parsed || upperBound != null && upperBound < parsed) {
                    isError = true
                    errorMessage = buildRangeErrorMessage(lowerBound, upperBound)
                    return@OutlinedTextField
                }
                isError = false
                errorMessage = ""
                onValueChange(parsed)
            },
            label = { Text(label) },
            isError = isError,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

fun TextFieldValue.toInt(): Int? {
    return try {
        Integer.parseInt(this.text)
    } catch (e: NumberFormatException) {
        null
    }
}

private fun buildRangeErrorMessage(lowerBound: Int? = null, upperBound: Int? = null): String {
    val rangeText = StringBuilder()
    if (lowerBound != null) {
        rangeText.append("$lowerBound ≤ ")
    }
    rangeText.append("value")
    if (upperBound != null) {
        rangeText.append(" ≤ $upperBound")
    }
    return "value not in range ($rangeText)"
}