package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatParticipantDto
import com.john_halaka.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toChatParticipant(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}