package com.john_halaka.core.domain.auth

import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email:String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote>     //emptyResult because it doesn't return any type of data


    suspend fun resendVerificationEmail(
        email: String
    ): EmptyResult<DataError.Remote>

    suspend fun verifyEmail(
        token: String
    ): EmptyResult<DataError.Remote>

}