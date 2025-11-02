package com.john_halaka.core.data.networking

import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import io.ktor.client.statement.HttpResponse


actual suspend fun <T> platformSafeCall(
    execute: suspend () -> HttpResponse,
    handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
    TODO("Not yet implemented")
}