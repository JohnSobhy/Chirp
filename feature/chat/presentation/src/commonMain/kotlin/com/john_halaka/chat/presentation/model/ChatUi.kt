package com.john_halaka.chat.presentation.model

import com.john_halaka.chat.domain.models.ChatMessage
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String?
)
