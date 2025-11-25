package com.john_halaka.core.data.auth

import com.john_halaka.core.data.dto.AuthInfoSerializable
import com.john_halaka.core.data.dto.requests.EmailRequest
import com.john_halaka.core.data.dto.requests.LoginRequest
import com.john_halaka.core.data.dto.requests.RegisterRequest
import com.john_halaka.core.data.mappers.toDomain
import com.john_halaka.core.data.networking.get
import com.john_halaka.core.data.networking.post
import com.john_halaka.core.domain.auth.AuthInfo
import com.john_halaka.core.domain.auth.AuthService
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.EmptyResult
import com.john_halaka.core.domain.util.Result
import com.john_halaka.core.domain.util.map
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = "/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        ).map { authInfoSerializable ->
            authInfoSerializable.toDomain()
        }
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                email = email,
                username = username,
                password = password
            )
        )
    }

    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(
                email = email
            )
        )

    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
}