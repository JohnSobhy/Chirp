package com.john_halaka.chat.data.chat

import com.john_halaka.chat.data.mappers.toChat
import com.john_halaka.chat.data.mappers.toChatEntity
import com.john_halaka.chat.data.mappers.toChatParticipantEntity
import com.john_halaka.chat.data.mappers.toLastMessageView
import com.john_halaka.chat.database.ChirpChatDatabase
import com.john_halaka.chat.database.enities.ChatWithParticipants
import com.john_halaka.chat.domain.chat.ChatRepository
import com.john_halaka.chat.domain.chat.ChatService
import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import com.john_halaka.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList.map { it.toChat() }
            }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toChatEntity(),
                        participants = chat.participants.map { it.toChatParticipantEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )
                }
                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantCrossRefDao,
                    messageDao = db.chatMessageDao

                )
            }
    }
}