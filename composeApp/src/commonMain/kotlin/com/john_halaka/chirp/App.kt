package com.john_halaka.chirp

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.john_halaka.auth.presentation.register.RegisterRoot
import com.john_halaka.designsystem.theme.ChirpTheme

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot(
            onRegisterSuccess = {},
            onLoginClick = {}
        )
    }
}