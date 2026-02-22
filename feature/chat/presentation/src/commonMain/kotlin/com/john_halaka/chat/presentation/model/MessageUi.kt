package com.john_halaka.chat.presentation.model

import com.john_halaka.chat.domain.models.ChatMessageDeliveryStatus
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi


sealed class MessageUi(open val id: String) {
    data class LocalUserMessage(
        override val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val isMenuOpen: Boolean,
        val formattedSentTime: UiText
    ) : MessageUi(id)

    data class OtherUserMessage(
        override val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ) : MessageUi(id)

    data class DateSeparator(
        override val id: String,
        val date: UiText,
    ) : MessageUi(id)
}