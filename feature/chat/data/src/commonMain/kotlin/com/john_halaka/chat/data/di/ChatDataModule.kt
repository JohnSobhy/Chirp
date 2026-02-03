package com.john_halaka.chat.data.di

import com.john_halaka.chat.data.chat.KtorChatParticipantService
import com.john_halaka.chat.domain.chat.ChatParticipantService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
}