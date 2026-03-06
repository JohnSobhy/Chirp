package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatDto
import com.john_halaka.chat.database.enities.ChatEntity
import com.john_halaka.chat.database.enities.ChatInfoEntity
import com.john_halaka.chat.database.enities.ChatWithParticipants
import com.john_halaka.chat.database.enities.MessageWithSender
import com.john_halaka.chat.domain.models.Chat
import com.john_halaka.chat.domain.models.ChatInfo
import com.john_halaka.chat.domain.models.ChatMessage
import com.john_halaka.chat.domain.models.ChatMessageDeliveryStatus
import com.john_halaka.chat.domain.models.ChatParticipant
import kotlin.time.Instant

typealias DataMessageWithSender = MessageWithSender
typealias DomainMessageWithSender = com.john_halaka.chat.domain.models.MessageWithSender

fun ChatDto.toChat(): Chat {
    return Chat(
        id = id,
        participants = participants.map { it.toChatParticipant() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toChatMessage()
    )
}

fun ChatEntity.toChat(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage? = null
): Chat {
    return Chat(
        id = chatId,
        participants = participants,
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = lastMessage
    )
}

fun ChatWithParticipants.toChat(): Chat {
    return Chat(
        id = chat.chatId,
        participants = participants.map { it.toChatParticipant() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toChatMessage()
    )
}

fun Chat.toChatEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds()
    )
}

fun DataMessageWithSender.toDomainMessageWithSender(): DomainMessageWithSender {
    return DomainMessageWithSender(
        message = message.toChatMessage(),
        sender = sender.toChatParticipant(),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(message.deliveryStatus)
    )
}

fun ChatInfoEntity.toChatInfo(): ChatInfo {
    return ChatInfo(
        chat = chat.toChat(
            participants = this.participants.map { it.toChatParticipant() },
            //leave last message as null, cause we are already passing all the messages
        ),
        messages = messagesWithSenders.map { it.toDomainMessageWithSender() }


    )
}