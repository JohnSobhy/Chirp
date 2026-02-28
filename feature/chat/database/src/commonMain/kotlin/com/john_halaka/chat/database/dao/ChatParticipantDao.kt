package com.john_halaka.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.john_halaka.chat.database.enities.ChatParticipantEntity

@Dao
interface ChatParticipantDao {
    @Upsert
    suspend fun upsertParticipant(participant: ChatParticipantEntity)

    @Upsert
    suspend fun upsertParticipants(participants: List<ChatParticipantEntity>)

    @Query("SELECT * FROM ChatParticipantEntity")
    suspend fun getAllParticipants(): List<ChatParticipantEntity>


}