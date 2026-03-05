package com.john_halaka.chat.database.enities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["chatId"],   // chatEntity here is the parent
            childColumns = ["chatId"],   // chatMessageEntity here is the child
            onDelete = ForeignKey.CASCADE
        //As this is a one-to-many relation then if the foreign key entity gets deleted
        // then this chatMessage entity, which is only exists in that chat, should be deleted
        )
    ],
    indices = [
        Index("chatId"),
        Index("timestamp"),
    ]
)
data class ChatMessageEntity(
    @PrimaryKey
    val messageId: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val deliveryStatus: String,
    val deliveryStatusTimestamp: Long = timestamp
)
