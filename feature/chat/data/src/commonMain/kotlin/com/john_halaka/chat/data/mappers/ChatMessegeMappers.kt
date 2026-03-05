package com.john_halaka.chat.data.mappers

import com.john_halaka.chat.data.dto.ChatMessageDto
import com.john_halaka.chat.database.enities.ChatMessageEntity
import com.john_halaka.chat.database.view.LastMessageView
import com.john_halaka.chat.domain.models.ChatMessage
import com.john_halaka.chat.domain.models.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageDto.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = Instant.parse(createdAt),
        deliveryStatus = ChatMessageDeliveryStatus.SENT
    )
}

fun LastMessageView.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = messageId,
        chatId = chatId,
        content = content,
        senderId = senderId,
        createdAt = Instant.fromEpochMilliseconds(timestamp),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(deliveryStatus)
    )
}


fun ChatMessage.toChatMessageEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        messageId = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        timestamp = createdAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name

    )
}

fun ChatMessage.toLastMessageView(): LastMessageView {
    return LastMessageView(
        messageId = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        timestamp = createdAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name

    )
}