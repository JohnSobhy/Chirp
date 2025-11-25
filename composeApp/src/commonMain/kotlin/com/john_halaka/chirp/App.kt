package com.john_halaka.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.john_halaka.chirp.navigation.DeepLinkListener
import com.john_halaka.chirp.navigation.NavigationRoot
import com.john_halaka.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    DeepLinkListener(navController)

    ChirpTheme {
        NavigationRoot(navController)
    }
}