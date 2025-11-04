package com.john_halaka.core.data.networking

import com.john_halaka.core.data.BuildKonfig
import com.john_halaka.core.domain.logging.ChirpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val chirpLogger: ChirpLogger
) {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        chirpLogger.debug(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(WebSockets) {
                pingIntervalMillis = 20_000L
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 20000L
                connectTimeoutMillis = 20000L
                socketTimeoutMillis = 20_000L
            }
            defaultRequest {
                header("x-api-key", BuildKonfig.API_KEY)
                contentType(ContentType.Application.Json)

            }
            expectSuccess = true
        }

    }
}