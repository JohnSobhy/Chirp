package com.john_halaka.chat.database.enities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ChatParticipantEntity(
    @PrimaryKey
    val userId: String,
    val username: String,
    val profilePictureUrl: String?,
)