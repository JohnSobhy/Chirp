package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatDto
import com.john_halaka.chat.database.enities.ChatEntity
import com.john_halaka.chat.database.enities.ChatWithParticipants
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

fun ChatWithParticipants.toChat(): Chat {
    return Chat(
        id = chat.chatId,
        participants = participants.map { it.toChatParticipant() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toChatMessage()
        )
}

fun Chat.toChatEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds()
    )
}