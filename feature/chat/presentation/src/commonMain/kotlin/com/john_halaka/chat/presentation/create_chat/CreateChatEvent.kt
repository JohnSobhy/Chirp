package com.john_halaka.chat.presentation.create_chat

import com.john_halaka.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat) : CreateChatEvent
}