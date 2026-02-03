package com.john_halaka.chat.data.chat

import com.john_halaka.chat.data.dto.ChatParticipantDto
import com.john_halaka.chat.data.mappers.toChatParticipant
import com.john_halaka.chat.domain.chat.ChatParticipantService
import com.john_halaka.chat.domain.models.ChatParticipant
import com.john_halaka.core.data.networking.get
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import com.john_halaka.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    private val httpClient: HttpClient
) : ChatParticipantService {
    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf("query" to query)
        ).map { it.toChatParticipant() }
    }


}