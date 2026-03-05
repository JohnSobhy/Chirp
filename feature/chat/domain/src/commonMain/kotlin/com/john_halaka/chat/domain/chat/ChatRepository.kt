package com.john_halaka.chat.domain.chat

import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}