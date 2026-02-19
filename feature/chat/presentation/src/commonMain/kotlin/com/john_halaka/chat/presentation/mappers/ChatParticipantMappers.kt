package com.john_halaka.chat.presentation.mappers

import com.john_halaka.chat.domain.models.ChatParticipant
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toChatParticipantUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        imageUrl = profilePictureUrl,
        initials = initials
    )
}