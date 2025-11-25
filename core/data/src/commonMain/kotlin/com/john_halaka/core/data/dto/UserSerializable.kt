package com.john_halaka.core.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class UserSerializable(
    val id: String,
    val username: String,
    val email: String,
    val hasVerifiedEmail: Boolean,
    val profilePictureUrl: String? = null,
)