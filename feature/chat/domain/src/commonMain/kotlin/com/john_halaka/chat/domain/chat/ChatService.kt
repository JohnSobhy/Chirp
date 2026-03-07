package com.john_halaka.chat.domain.chat

import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.EmptyResult
import com.john_halaka.core.domain.util.Result

interface ChatService {
    suspend fun createChat(
        otherUserIds: List<String>
    ): Result<Chat, DataError.Remote>

    suspend fun getChats(): Result<List<Chat>, DataError.Remote>

    suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote>

    suspend fun leaveChat(chatId: String) : EmptyResult<DataError.Remote>
}