package com.john_halaka.chirp

import androidx.compose.runtime.Composable
import com.john_halaka.chirp.navigation.NavigationRoot
import com.john_halaka.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}