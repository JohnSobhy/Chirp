package com.john_halaka.designsystem.components.textFields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.john_halaka.designsystem.theme.ChirpTheme
import com.john_halaka.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    ChirpTextFieldLayout(
        title = title,
        supportingText = supportingText,
        isError = isError,
        enabled = enabled,
        onFocusChanged = onFocusChanged,
        modifier = modifier
    ) { styleModifier, interactionSource ->
        BasicTextField(
            state = state,
            enabled = enabled,
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else MaterialTheme.colorScheme.extended.textPlaceholder
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.onSurface
            ),
            interactionSource = interactionSource,
            modifier = styleModifier,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerBox()
                }
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter a valid email",
            modifier = Modifier.width(300.dp)

        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpTextFieldFilledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "test@test.com"
            ),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter a valid email",
            modifier = Modifier.width(300.dp)

        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpTextFieldDisabledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "test@test.com"
            ),
            enabled = false,
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter a valid email",
            modifier = Modifier.width(300.dp)

        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpTextFieldErrorPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "test@test.com"
            ),
            isError = true,
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "the email is not valid",
            modifier = Modifier.width(300.dp)

        )
    }
}