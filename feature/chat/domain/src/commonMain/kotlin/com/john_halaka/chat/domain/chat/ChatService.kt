package com.john_halaka.chat.domain.chat

import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result

interface ChatService {
    suspend fun createChat(
        otherUserIds: List<String>
    ): Result<Chat, DataError.Remote>

}