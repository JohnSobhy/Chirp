package com.john_halaka.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.john_halaka.chat.data.chat.KtorChatParticipantService
import com.john_halaka.chat.data.chat.KtorChatService
import com.john_halaka.chat.data.chat.OfflineFirstChatRepository
import com.john_halaka.chat.database.DatabaseFactory
import com.john_halaka.chat.domain.chat.ChatParticipantService
import com.john_halaka.chat.domain.chat.ChatRepository
import com.john_halaka.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformChatDataModule: Module
val chatDataModule = module {
    includes(platformChatDataModule)
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class

    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}