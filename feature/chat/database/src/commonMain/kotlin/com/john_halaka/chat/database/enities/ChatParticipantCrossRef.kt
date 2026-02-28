package com.john_halaka.chat.database.enities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    primaryKeys = [
        "chatId",
        "userId"
    ],
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["chatId"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChatParticipantEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
) // this setup is for many to many relations as a single user can have multiple chats and a single chat can have multiple users
// deleting any of the 2 will result in deleting the instance of this cross ref entity, as if the chat or the user is no longer in the db, we can't have them related to another table
data class ChatParticipantCrossRef(
    val chatId: String,
    val userId: String,
    val isActive: Boolean,    //this is important for if a participant leaves a chat we still need to keep their messages in the chat not delete them
    // So we use this flag to see if the user is in the chat or not any more without deleting msgs
)
