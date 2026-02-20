package com.john_halaka.chat.presentation.chat_list

import com.john_halaka.chat.presentation.model.ChatUi
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.designsystem.components.avatar.ChatParticipantUi

data class ChatListState(
    val chats: List<ChatUi> = emptyList(),
    val error: UiText? = null,
    val localParticipant: ChatParticipantUi? = null,
    val isUserMenuOpen: Boolean = false,
    val showLogoutConfirmation: Boolean = false,
    val selectedChatId: String? = null,
    val isLoading: Boolean = false,
)