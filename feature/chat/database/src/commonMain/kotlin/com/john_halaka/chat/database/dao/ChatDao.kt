package com.john_halaka.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.john_halaka.chat.database.enities.ChatEntity
import com.john_halaka.chat.database.enities.ChatInfoEntity
import com.john_halaka.chat.database.enities.ChatMessageEntity
import com.john_halaka.chat.database.enities.ChatParticipantCrossRef
import com.john_halaka.chat.database.enities.ChatParticipantEntity
import com.john_halaka.chat.database.enities.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat(chat: ChatEntity)

    @Upsert
    suspend fun upsertChats(chats: List<ChatEntity>)

    @Query("DELETE FROM ChatEntity WHERE chatId = :chatId")
    suspend fun deleteChatById(chatId: String)

    @Transaction    //When defining the function block yourself make it a db transaction
    suspend fun deleteChatsByIds(chatIds: List<String>) {
        chatIds.forEach { chatId ->
            deleteChatById(chatId)
        }
    }

    @Transaction
    @Query("SELECT * FROM ChatEntity ORDER BY lastActivityAt DESC")
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>


    @Query("SELECT * FROM ChatEntity WHERE chatId = :chatId")
    suspend fun getChatById(chatId: String): ChatEntity?

    @Query("DELETE FROM ChatEntity")
    suspend fun deleteAllChats()

    @Query("SELECT chatId FROM ChatEntity")
    suspend fun getAllChatIds(): List<String>

    @Query("SELECT COUNT(*) FROM ChatEntity")
    fun getChatCount(): Flow<Int>

    @Query(
        """
    SELECT p.* FROM ChatParticipantEntity p 
    JOIN ChatParticipantCrossRef cpcr ON p.userId = cpcr.userId
    WHERE cpcr.chatId = :chatId AND cpcr.isActive = true
    ORDER BY p.username
        """
    )
    fun getActiveParticipantByChatId(chatId: String): Flow<List<ChatParticipantEntity>>

    @Query(
        """
            SELECT c.*
            FROM chatentity c
            WHERE c.chatId = :chatId
        """
    )
    @Transaction
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity?>

    @Transaction
    suspend fun upsertChatWithParticipantsAndCrossRefs(
        chat: ChatEntity,
        participants: List<ChatParticipantEntity>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantCrossRefDao
    ) {
        upsertChat(chat)
        participantDao.upsertParticipants(participants)

        val crossRefs = participants.map { participant ->
            ChatParticipantCrossRef(
                chatId = chat.chatId,
                userId = participant.userId,
                isActive = true
            )
        }
        crossRefDao.upsertCrossRefs(crossRefs)
        crossRefDao.syncChatParticipants(
            chatId = chat.chatId,
            participants = participants
        )
    }

    @Transaction
    suspend fun upsertChatsWithParticipantsAndCrossRefs(
        chats: List<ChatWithParticipants>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantCrossRefDao,
        messageDao: ChatMessageDao
    ) {
        upsertChats(chats.map { it.chat })
        val localChatIds = getAllChatIds()
        val serverChatIds = chats.map { it.chat.chatId }
        val staleChatIds = localChatIds.filter { !serverChatIds.contains(it) }


        val allParticipants = chats.flatMap { it.participants }
        participantDao.upsertParticipants(allParticipants)

        val allCrossRefs = chats.flatMap { chatWithParticipants ->
            chatWithParticipants.participants.map { participant ->
                ChatParticipantCrossRef(
                    chatId = chatWithParticipants.chat.chatId,
                    userId = participant.userId,
                    isActive = true
                )
            }
        }
        crossRefDao.upsertCrossRefs(allCrossRefs)

        chats.forEach { (chat, participants, lastMessage) ->
            crossRefDao.syncChatParticipants(
                chatId = chat.chatId,
                participants = participants
            )
            val lastMessageEntity = lastMessage?.run {
                ChatMessageEntity(
                    messageId = messageId,
                    chatId = chatId,
                    content = content,
                    senderId = senderId,
                    timestamp = timestamp,
                    deliveryStatus = deliveryStatus
                )
            }
            if (lastMessageEntity != null) {
                messageDao.upsertMessage(lastMessageEntity)
            }
        }

        deleteChatsByIds(staleChatIds)
    }
}
