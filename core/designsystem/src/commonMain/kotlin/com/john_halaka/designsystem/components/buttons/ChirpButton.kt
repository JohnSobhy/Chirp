package com.john_halaka.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.john_halaka.designsystem.theme.ChirpTheme
import com.john_halaka.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChirpButtonStyle {
    PRIMARY,
    DESTRUCTIVE_PRIMARY,
    SECONDARY,
    DESTRUCTIVE_SECONDARY,
    TEXT
}

@Composable
fun ChirpButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = when (style) {
        ChirpButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled
        )

        ChirpButtonStyle.DESTRUCTIVE_PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
            disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled
        )

        ChirpButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = Transparent,
            contentColor = MaterialTheme.colorScheme.extended.textSecondary,
            disabledContainerColor = Transparent,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled
        )

        ChirpButtonStyle.DESTRUCTIVE_SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = Transparent,
            contentColor = MaterialTheme.colorScheme.error,
            disabledContainerColor = Transparent,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled
        )

        ChirpButtonStyle.TEXT -> ButtonDefaults.buttonColors(
            containerColor = Transparent,
            contentColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = Transparent,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled
        )
    }

    val defaultBorderStroke =
        BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.extended.disabledOutline
        )

    val border = when {
        style == ChirpButtonStyle.PRIMARY && !enabled -> defaultBorderStroke
        style == ChirpButtonStyle.SECONDARY -> defaultBorderStroke
        style == ChirpButtonStyle.DESTRUCTIVE_PRIMARY && !enabled -> defaultBorderStroke
        style == ChirpButtonStyle.DESTRUCTIVE_SECONDARY -> {
            val borderColor = if (enabled) {
                MaterialTheme.colorScheme.extended.destructiveSecondaryOutline
            } else {
                MaterialTheme.colorScheme.extended.disabledOutline
            }
            BorderStroke(
                width = 1.dp,
                color = borderColor
            )
        }

        else -> null
    }
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        border = border
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(6.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(
                        alpha = if (isLoading) 1f else 0f
                    ),
                strokeWidth = 1.5.dp,
                color = Black
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(
                    alpha = if (isLoading) 0f else 1f
                )
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Preview
@Composable
fun ChirpPrimaryButtonPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpButton(
            text = "Hello Primary!",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun ChirpSecondaryButtonPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpButton(
            text = "Hello Secondary!",
            onClick = {},
            style = ChirpButtonStyle.SECONDARY

        )
    }
}

@Preview
@Composable
fun ChirpDestructivePrimaryButtonPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpButton(
            text = "Hello DestructivePrimary!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_PRIMARY

        )
    }
}

@Preview
@Composable
fun ChirpDestructiveSecondaryButtonPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpButton(
            text = "Hello DestructiveSecondary!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY

        )
    }
}

@Preview
@Composable
fun ChirpTextButtonPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpButton(
            text = "Hello Text!",
            onClick = {},
            style = ChirpButtonStyle.TEXT
        )
    }
}
