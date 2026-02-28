package com.john_halaka.chat.database.enities

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithSender(
    @Embedded
    val message: ChatMessageEntity,
    @Relation(
        parentColumn = "senderId",
        entityColumn = "userId"
        // no need for junction because we are not using a many to many relation but a one to many
    )
    val sender: ChatParticipantEntity
)
