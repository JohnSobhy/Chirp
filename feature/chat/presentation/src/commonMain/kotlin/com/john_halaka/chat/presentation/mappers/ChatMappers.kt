package com.john_halaka.chat.presentation.mappers

import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.chat.presentation.model.ChatUi

fun Chat.toChatUi(localParticipantId: String): ChatUi {
    val (local, other) = participants.partition { it.userId == localParticipantId }
    return ChatUi(
        id = id,
        localParticipant = local.first().toChatParticipantUi(),
        otherParticipants = other.map { it.toChatParticipantUi() },
        lastMessage = lastMessage,
        lastMessageSenderUsername = participants.find { it.userId == lastMessage?.senderId }?.username

    )
}