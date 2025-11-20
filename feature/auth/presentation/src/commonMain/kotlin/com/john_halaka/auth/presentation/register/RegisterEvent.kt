package com.john_halaka.auth.presentation.register

sealed interface RegisterEvent {
    data class Success(val email: String) : RegisterEvent
    data object LoginButtonClick : RegisterEvent
}