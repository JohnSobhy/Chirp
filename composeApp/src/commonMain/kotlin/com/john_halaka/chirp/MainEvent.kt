package com.john_halaka.chirp

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}