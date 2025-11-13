package com.john_halaka.designsystem.components.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.john_halaka.core.presentation.util.DeviceConfiguration
import com.john_halaka.core.presentation.util.currentDeviceConfiguration
import com.john_halaka.designsystem.components.brand.ChirpBrandLogo
import com.john_halaka.designsystem.theme.ChirpTheme
import com.john_halaka.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveFormLayout(
    headerText: String,
    errorText: String? = null,
    logo: @Composable () -> Unit,
    formContent: @Composable ColumnScope. () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = currentDeviceConfiguration()
    val headerColor = if (configuration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.extended.textPrimary
    }

    when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ChirpSurface(
                modifier = modifier
                    .consumeWindowInsets(WindowInsets.navigationBars)    //the space taken by navigation bar at the bottom
                    .consumeWindowInsets(WindowInsets.displayCutout),    //the space for the camera and notch
                header = {
                    Spacer(Modifier.height(32.dp))
                    logo()
                    Spacer(Modifier.height(32.dp))
                }
            ) {
                Spacer(Modifier.height(24.dp))
                AuthHeaderSection(
                    headerText = headerText,
                    errorText = errorText,
                    headerColor = headerColor
                )
                Spacer(Modifier.height(24.dp))
                formContent()
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .fillMaxSize()
                    .consumeWindowInsets(WindowInsets.navigationBars)
                    .consumeWindowInsets(WindowInsets.displayCutout)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    logo()
                    AuthHeaderSection(
                        headerText = headerText,
                        errorText = errorText,
                        headerColor = headerColor
                    )
                }

                ChirpSurface(
                    modifier = Modifier.weight(1f)
                ) {
                    formContent()
                }
            }
        }

        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                logo()
                Column(
                    modifier = Modifier
                        .widthIn(max = 480.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    AuthHeaderSection(
                        headerText = headerText,
                        errorText = errorText,
                        headerColor = headerColor
                    )
                    formContent()
                }
            }
        }
    }

}

@Composable
fun ColumnScope.AuthHeaderSection(
    headerText: String,
    errorText: String? = null,
    headerColor: Color,
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        color = headerColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    AnimatedVisibility(
        visible = errorText != null,
    ) {
        if (errorText != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = errorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chirp",
            errorText = "Login Failed",
            logo = {
                ChirpBrandLogo()
            },
            formContent = {
                Text(
                    "Sample text",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        )
    }
}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutDarkPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chirp",
            errorText = "Login Failed",
            logo = {
                ChirpBrandLogo()
            },
            formContent = {
                Text(
                    "Sample text",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        )
    }
}