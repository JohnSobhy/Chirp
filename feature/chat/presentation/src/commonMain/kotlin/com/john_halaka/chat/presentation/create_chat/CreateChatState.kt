package com.john_halaka.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isSearching: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null,
    val isCreatingChat: Boolean = false,
    val createChatError: UiText? = null
)