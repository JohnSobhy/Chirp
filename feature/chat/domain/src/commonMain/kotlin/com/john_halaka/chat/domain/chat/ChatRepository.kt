package com.john_halaka.chat.domain.chat

import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.chat.domain.models.ChatInfo
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.EmptyResult
import com.john_halaka.core.domain.util.Result
import kotlinx.coroutines.flow.Flow


interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getChatInfoById(chatId: String): Flow<ChatInfo>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
    suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote>

    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>
}