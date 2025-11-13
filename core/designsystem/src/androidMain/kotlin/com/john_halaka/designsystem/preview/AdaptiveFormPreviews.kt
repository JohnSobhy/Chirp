package com.john_halaka.designsystem.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.john_halaka.designsystem.components.brand.ChirpBrandLogo
import com.john_halaka.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.john_halaka.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.john_halaka.designsystem.theme.ChirpTheme


@Composable
@PreviewLightDark
@PreviewScreenSizes
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
@PreviewLightDark
@PreviewScreenSizes
fun ChirpAdaptiveResultLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveResultLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Registration Successful",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}