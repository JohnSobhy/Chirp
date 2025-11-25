package com.john_halaka.core.domain.auth

data class User(
    val id: String,
    val username: String,
    val email: String,
    val hasVerifiedEmail: Boolean,
    val profilePictureUrl: String? = null,
)