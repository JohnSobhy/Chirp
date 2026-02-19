package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatMessageDto
import com.john_halaka.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = Instant.parse(createdAt)
    )
}