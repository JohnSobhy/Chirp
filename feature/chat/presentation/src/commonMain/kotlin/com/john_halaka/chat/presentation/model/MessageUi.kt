package com.john_halaka.chat.presentation.model

import com.john_halaka.chat.domain.models.ChatMessageDeliveryStatus
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi


sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val isMenuOpen: Boolean,
        val formattedSentTime: UiText
    ): MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ): MessageUi

    data class DateSeparator(
        val id: String,
        val date: UiText,
    ): MessageUi
}