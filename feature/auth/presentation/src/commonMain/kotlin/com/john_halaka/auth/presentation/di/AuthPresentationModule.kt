package com.john_halaka.auth.presentation.di

import com.john_halaka.auth.presentation.email_verification.EmailVerificationViewModel
import com.john_halaka.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.john_halaka.auth.presentation.login.LoginViewModel
import com.john_halaka.auth.presentation.register.RegisterViewModel
import com.john_halaka.auth.presentation.register_success.RegisterSuccessViewModel
import com.john_halaka.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}

