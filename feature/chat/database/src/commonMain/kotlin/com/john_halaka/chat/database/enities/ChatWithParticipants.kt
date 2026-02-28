package com.john_halaka.chat.database.enities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ChatWithParticipants(
    @Embedded     //one chat
    val chat: ChatEntity,
    @Relation(
        parentColumn = "chatId",  // parent is the embedded entity
        entityColumn = "userId",    //the primary key of the list of participants
        associateBy = Junction(ChatParticipantCrossRef::class)  // here we tell room to associate both these tables using the cross ref table
    )
    val participants: List<ChatParticipantEntity>
)

data class ChatInfoEntity(
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
        entity = ChatMessageEntity::class,
        //we use this entity to query the entityColumn "chatId" getting all the ChatMessages
        // which is the embedded entity in the MessageWithSender
        // that we are trying to get a list of
    )
    val messagesWithSenders: List<MessageWithSender>
)
