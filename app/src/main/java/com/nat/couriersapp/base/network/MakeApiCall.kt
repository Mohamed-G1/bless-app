package com.nat.couriersapp.base.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

suspend fun <T> makeFlowAPiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T
) = flow {
    emit(Resource.Loading(true))
    val result = block()
    emit(Resource.Loading(false))
    emit(Resource.Success(result))
}.flowOn(dispatcher).catch { t ->
    emit(Resource.Loading(false))
    emit(
        Resource.Error(
            message = t.message.orEmpty(),
            exception = t
        )
    )
}

suspend fun <T> makeOneTimeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T
): Resource<T> {
    return try {
        // Loading state before making the API call
        withContext(dispatcher) {
            Resource.Loading(true).also {
                val result = block()  // Perform the API call
                Resource.Loading(false)  // Set loading to false
                Resource.Success(result)  // Return the result as success
            }
        }
    } catch (t: Throwable) {
        // In case of an error, return the error state
        Resource.Loading(false).also {
            Resource.Error(
                message = t.message.orEmpty(),
                exception = t
            )
        }
    }
}
