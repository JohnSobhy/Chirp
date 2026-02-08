package com.john_halaka.chat.data.chat

import com.john_halaka.chat.data.dto.ChatDto
import com.john_halaka.chat.data.dto.request.CreateChatRequest
import com.john_halaka.chat.data.mappers.toChat
import com.john_halaka.chat.domain.chat.ChatService
import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.core.data.networking.post
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import com.john_halaka.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(
    private val httpClient: HttpClient
) : ChatService {
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chat",
            body = CreateChatRequest(otherUserIds)
        ).map { chatDto ->
            chatDto.toChat()
        }
    }
}