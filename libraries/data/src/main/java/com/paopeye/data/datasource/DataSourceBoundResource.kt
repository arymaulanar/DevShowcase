package com.paopeye.data.datasource

import com.paopeye.data.entity.ResponseEntity
import com.paopeye.data.entity.ResponseEntity.Companion.SUCCESS_RESPONSE_CODE
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.datastate.StateMessage
import com.paopeye.kit.extension.emptyIndex
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

internal class DataStateBoundResource<ENTITY : ToModelMapper<MODEL>, MODEL> private constructor(
    private val isRetryServerError: Boolean = false,
    private val networkCall: (suspend () -> ResponseEntity<ENTITY>?)? = null,
    private val cacheCall: (suspend () -> ResponseEntity<ENTITY>?)? = null,
    private val updateCache: ((ENTITY?) -> Unit)? = null
) {
    private var retryServerErrorCount = emptyInt()

    suspend fun getResult(): DataState<MODEL> {
        if (cacheCall != null) return safeCacheCall()
        if (networkCall != null) return safeNetworkCall()
        return createDataStateError(code = emptyIndex().toString())
    }

    private suspend fun safeNetworkCall(
    ): DataState<MODEL> {
        var dataState = createDataStateError(code = emptyIndex().toString())
        try {
            val result = networkCall?.invoke() ?: return dataState
            updateCache?.invoke(result.data)
            dataState = handleDataStateResponse(result)
        } catch (throwable: SocketTimeoutException) {
            dataState = createDataStateError(code = SOCKET_TIMEOUT)
        } catch (throwable: CancellationException) {
            dataState = createDataStateError(code = NO_INTERNET_CONNECTION)
        } catch (throwable: ConnectException) {
            dataState = createDataStateError(code = NO_INTERNET_CONNECTION)
        } catch (throwable: UnknownHostException) {
            dataState = createDataStateError(code = NO_INTERNET_CONNECTION)
        } catch (throwable: HttpException) {
            val code = getHttpCode(throwable)
            val errorCode = code.takeIf { it == UNAUTHORIZED_CODE } ?: emptyIndex()
            dataState = createDataStateError(code = errorCode.toString())
            checkServerError(code)?.let { return it }
        } catch (throwable: Throwable) {
            dataState = createDataStateError(code = emptyIndex().toString())
        }
        return dataState
    }

    private suspend fun safeCacheCall(): DataState<MODEL> {
        try {
            cacheCall?.invoke()?.let { return processSuccess(it.data) }
        } catch (throwable: Throwable) {
            return createDataStateError(code = emptyIndex().toString())
        }
        return createDataStateError(code = emptyIndex().toString())
    }

    private fun getHttpCode(throwable: HttpException): Int {
        return try {
            throwable.response()?.code() ?: emptyIndex()
        } catch (exception: Exception) {
            emptyIndex()
        }
    }

    private fun handleDataStateResponse(response: ResponseEntity<ENTITY>): DataState<MODEL> {
        return when (response.responseCode == SUCCESS_RESPONSE_CODE) {
            true -> processSuccess(response.data)
            false -> processNetworkError(response)
        }
    }

    private fun processNetworkError(response: ResponseEntity<ENTITY>) =
        createDataStateError(
            code = response.responseCode,
            title = response.responseTitle,
            message = response.responseDescription,
            descriptions = response.responseDescriptions,
            footnote = response.responseFootnote
        )

    private fun processSuccess(entity: ENTITY?) = DataState.SUCCESS(entity?.toModel())

    private fun createDataStateError(
        code: String? = emptyString(),
        title: String? = emptyString(),
        message: String? = emptyString(),
        descriptions: List<String>? = emptyList(),
        footnote: String? = emptyString()
    ): DataState<MODEL> {
        return DataState.ERROR(
            StateMessage(
                code = code,
                title = title,
                message = message,
                descriptions = descriptions,
                footnote = footnote
            )
        )
    }

    private suspend fun checkServerError(code: Int): DataState<MODEL>? {
        val isServerErrorCode = code in SERVER_ERROR_CODES
        if (isServerErrorCode &&
            isRetryServerError &&
            retryServerErrorCount < MAX_RETRY
        ) {
            delay(DELAY_IN_MILLIS)
            retryServerErrorCount++
            return safeNetworkCall()
        }
        return null
    }

    companion object {
        private const val SOCKET_TIMEOUT = "SOCKET_TIMEOUT"
        private const val NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION"
        private const val MAX_RETRY = 2
        private const val DELAY_IN_MILLIS = 500L
        private const val UNAUTHORIZED_CODE = 401
        private val SERVER_ERROR_CODES = 500..599

        fun <ENTITY : ToModelMapper<MODEL>, MODEL> createNetworkCall(
            isRetryServerError: Boolean = false,
            updateCache: ((ENTITY?) -> Unit)? = null,
            networkCall: (suspend () -> ResponseEntity<ENTITY>?)
        ): DataStateBoundResource<ENTITY, MODEL> = DataStateBoundResource(
            isRetryServerError,
            networkCall = networkCall,
            updateCache = updateCache
        )

        fun <ENTITY : ToModelMapper<MODEL>, MODEL> createCacheCall(
            cacheCall: (suspend () -> ResponseEntity<ENTITY>?)
        ): DataStateBoundResource<ENTITY, MODEL> = DataStateBoundResource(cacheCall = cacheCall)
    }
}
