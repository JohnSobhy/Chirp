package com.john_halaka.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.john_halaka.core.presentation.util.UiText

data class LoginState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
    val error: UiText? = null


)