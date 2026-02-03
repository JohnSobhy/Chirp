package com.john_halaka.chat.domain.chat

import com.john_halaka.chat.domain.models.ChatParticipant
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query: String
    ): Result<ChatParticipant, DataError.Remote>

}