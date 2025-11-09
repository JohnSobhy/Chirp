package com.john_halaka.designsystem.components.textFields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
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
fun ChirpPasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean,
    onToggleVisibilityClick: () -> Unit,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    ChirpTextFieldLayout(
        modifier = modifier,
        title = title,
        supportingText = supportingText,
        isError = isError,
        enabled = enabled,
        onFocusChanged = onFocusChanged,
    ) { styleModifier, interactionSource ->
        BasicSecureTextField(
            state = state,
            modifier = styleModifier,
            enabled = enabled,
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.Hidden
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            interactionSource = interactionSource,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else MaterialTheme.colorScheme.extended.textPlaceholder
            ),
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.onSurface
            ),
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
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
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpPasswordTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState(),
            placeholder = "password",
            title = "Password",
            supportingText = "Please enter your password",
            modifier = Modifier.width(300.dp),
            isPasswordVisible = true,
            onToggleVisibilityClick = {}

        )
    }
}


@Composable
@Preview(
    showBackground = true,
)
fun ChirpPasswordTextFieldFilledPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState("password123"),
            placeholder = "password",
            title = "Password",
            supportingText = "Please enter your password",
            modifier = Modifier.width(300.dp),
            isPasswordVisible = false,
            onToggleVisibilityClick = {}

        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun ChirpPasswordTextFieldErrorPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState("password123"),
            placeholder = "password",
            title = "Password",
            supportingText = "doesn't contain an uppercase character",
            modifier = Modifier.width(300.dp),
            isPasswordVisible = true,
            onToggleVisibilityClick = {},
            isError = true

        )
    }
}
