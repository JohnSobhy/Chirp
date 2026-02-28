package com.john_halaka.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.john_halaka.chat.database.enities.ChatEntity
import com.john_halaka.chat.database.enities.ChatInfoEntity
import com.john_halaka.chat.database.enities.ChatParticipantEntity
import com.john_halaka.chat.database.enities.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat( chat: ChatEntity)

    @Upsert
    suspend fun upsertChats( chats: List<ChatEntity>)

    @Query("DELETE FROM ChatEntity WHERE chatId = :chatId")
    suspend fun deleteChatById(chatId: String)

    @Transaction    //When defining the function block yourself make it a db transaction
    suspend fun deleteChatsByIds(chatIds: List<String>){
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
    suspend fun getAllChatIds() : List<String>

    @Query("SELECT COUNT(*) FROM ChatEntity")
    fun getChatCount(): Flow<Int>

    @Query("""
    SELECT p.* FROM ChatParticipantEntity p 
    JOIN ChatParticipantCrossRef cpcr ON p.userId = cpcr.userId
    WHERE cpcr.chatId = :chatId AND cpcr.isActive = true
    ORDER BY p.username
        """)
    fun getActiveParticipantByChatId(chatId: String): Flow<List<ChatParticipantEntity>>

    @Query("SELECT * FROM ChatEntity WHERE chatId = :chatId")
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity?>
}