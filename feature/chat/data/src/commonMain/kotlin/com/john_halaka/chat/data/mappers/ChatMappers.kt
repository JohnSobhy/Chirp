package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatDto
import com.john_halaka.chat.domain.models.Chat
import kotlin.time.Instant

fun ChatDto.toChat(): Chat{
    return Chat(
        id = id,
        participants = participants.map { it.toChatParticipant() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toChatMessage()
    )
}