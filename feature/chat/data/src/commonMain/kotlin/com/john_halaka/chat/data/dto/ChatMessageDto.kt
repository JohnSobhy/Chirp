package com.john_halaka.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String

)
