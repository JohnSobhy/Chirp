package com.john_halaka.auth.presentation.di

import com.john_halaka.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module{
    viewModelOf(::RegisterViewModel)
}

