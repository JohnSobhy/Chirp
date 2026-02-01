package com.john_halaka.chirp.di

import com.john_halaka.auth.presentation.di.authPresentationModule
import com.john_halaka.chat.presentation.di.chatPresentationModule
import com.john_halaka.core.data.di.coreDataModule
import com.john_halaka.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {

    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            corePresentationModule,
            authPresentationModule,
            chatPresentationModule,
            appModule
        )
    }
}