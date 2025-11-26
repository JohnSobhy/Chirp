package com.john_halaka.core.data.di

import com.john_halaka.core.data.auth.DataStoreSessionStorage
import com.john_halaka.core.data.auth.KtorAuthService
import com.john_halaka.core.data.logging.KermitLogger
import com.john_halaka.core.data.networking.HttpClientFactory
import com.john_halaka.core.domain.auth.AuthService
import com.john_halaka.core.domain.auth.SessionStorage
import com.john_halaka.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<ChirpLogger> { KermitLogger }
    single {
        HttpClientFactory(get(), get()).create(get())
    }

    singleOf(::KtorAuthService) bind AuthService::class
    singleOf(::DataStoreSessionStorage) bind SessionStorage::class
}