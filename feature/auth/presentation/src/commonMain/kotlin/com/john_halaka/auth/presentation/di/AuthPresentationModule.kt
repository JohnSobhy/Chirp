package com.john_halaka.auth.presentation.di

import com.john_halaka.auth.presentation.email_verification.EmailVerificationViewModel
import com.john_halaka.auth.presentation.login.LoginViewModel
import com.john_halaka.auth.presentation.register.RegisterViewModel
import com.john_halaka.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module{
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
}

