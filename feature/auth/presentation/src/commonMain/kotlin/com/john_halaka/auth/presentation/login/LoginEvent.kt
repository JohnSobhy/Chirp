package com.john_halaka.auth.presentation.login

sealed interface LoginEvent {
    data object Success : LoginEvent
}
