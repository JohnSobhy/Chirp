package com.john_halaka.chat.database.enities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.john_halaka.chat.database.view.LastMessageView

data class ChatWithParticipants(
    @Embedded     //one chat
    val chat: ChatEntity,
    @Relation(
        parentColumn = "chatId",  // parent is the embedded entity
        entityColumn = "userId",    //the primary key of the list of participants
        associateBy = Junction(ChatParticipantCrossRef::class)  // here we tell room to associate both these tables using the cross ref table
    )
    val participants: List<ChatParticipantEntity>,

    @Relation(
        parentColumn = "chatId",
        entityColumn = "chatId",
        entity = LastMessageView::class
    )
    val lastMessage: LastMessageView?
)

