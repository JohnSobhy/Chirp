package com.john_halaka.chat.presentation.chat_detail

import com.john_halaka.core.presentation.util.UiText

interface ChatDetailEvent {
    data object OnChatLeft : ChatDetailEvent
    data class OnError(val error: UiText) : ChatDetailEvent

}