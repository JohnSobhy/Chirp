package com.john_halaka.core.data.networking

import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.Result
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

actual suspend fun <T> platformSafeCall(
    execute: suspend () -> HttpResponse,
    handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        println("inside try block of platformSafeCall response is ${response.status.value}")
        handleResponse(response)
    } catch(e: UnknownHostException) {
        Result.Failure(DataError.Remote.NO_INTERNET)
    } catch(e: UnresolvedAddressException) {
        Result.Failure(DataError.Remote.NO_INTERNET)
    } catch(e: ConnectException) {
        Result.Failure(DataError.Remote.NO_INTERNET)
    } catch(e: SocketTimeoutException) {
        Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: HttpRequestTimeoutException) {
        Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: SerializationException) {
        Result.Failure(DataError.Remote.SERIALIZATION_ERROR)
    }
    catch (e: ClientRequestException) {
        currentCoroutineContext().ensureActive()
        println("inside catch block of platformSafeCall response is ${e.response.status.value}")
        Result.Failure(DataError.Remote.SERVICE_UNAVAILABLE)
    }
    catch (e: Exception){
        currentCoroutineContext().ensureActive()
        Result.Failure(DataError.Remote.UNKNOWN)
    }

}