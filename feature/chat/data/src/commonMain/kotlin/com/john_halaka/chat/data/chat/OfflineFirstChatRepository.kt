package com.john_halaka.chat.data.chat

import com.john_halaka.chat.data.mappers.toChat
import com.john_halaka.chat.data.mappers.toChatEntity
import com.john_halaka.chat.data.mappers.toChatInfo
import com.john_halaka.chat.data.mappers.toChatParticipantEntity
import com.john_halaka.chat.data.mappers.toLastMessageView
import com.john_halaka.chat.database.ChirpChatDatabase
import com.john_halaka.chat.database.enities.ChatWithParticipants
import com.john_halaka.chat.domain.chat.ChatRepository
import com.john_halaka.chat.domain.chat.ChatService
import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.chat.domain.models.ChatInfo
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.EmptyResult
import com.john_halaka.core.domain.util.Result
import com.john_halaka.core.domain.util.asEmptyResult
import com.john_halaka.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
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

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> {
        return db.chatDao
            .getChatInfoById(chatId)
            .filterNotNull()
            .map { chatInfoEntity ->
                chatInfoEntity.toChatInfo()
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

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> {
        return chatService
            .getChatById(chatId)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toChatEntity(),
                    participants = chat.participants.map { it.toChatParticipantEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantCrossRefDao
                )
            }
            .asEmptyResult()

    }

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return chatService
            .createChat(otherUserIds)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toChatEntity(),
                    participants = chat.participants.map { it.toChatParticipantEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantCrossRefDao
                )
            }
    }
}