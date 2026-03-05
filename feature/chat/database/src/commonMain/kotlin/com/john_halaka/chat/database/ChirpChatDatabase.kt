package com.john_halaka.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.john_halaka.chat.database.dao.ChatDao
import com.john_halaka.chat.database.dao.ChatMessageDao
import com.john_halaka.chat.database.dao.ChatParticipantCrossRefDao
import com.john_halaka.chat.database.dao.ChatParticipantDao
import com.john_halaka.chat.database.enities.ChatEntity
import com.john_halaka.chat.database.enities.ChatMessageEntity
import com.john_halaka.chat.database.enities.ChatParticipantCrossRef
import com.john_halaka.chat.database.enities.ChatParticipantEntity
import com.john_halaka.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatMessageEntity::class,
        ChatParticipantEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [
        LastMessageView::class,
    ],
    version = 1
)
abstract class ChirpChatDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatParticipantCrossRefDao: ChatParticipantCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}