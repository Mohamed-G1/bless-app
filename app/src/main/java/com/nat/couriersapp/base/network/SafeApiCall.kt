package com.nat.couriersapp.base.network

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall.invoke()
        when {
            response.isSuccessful -> {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("HTTP 200: Empty response body")
            }
            else -> {
                Resource.Error("HTTP Error: ${response.code()}")
            }
        }
    } catch (exception: Throwable) {
        handleApiError(exception)
    }
}

fun <T> handleApiError(exception: Throwable): Resource<T> {
    val message = when (exception) {
        is TimeoutException -> "Request time out. Please try again"
        is IOException -> "Network error/ please check your connection"
        is HttpException -> {
            val statusCode = exception.code()
            when (statusCode) {
                400 -> "Bad Request"
                401 -> "Unauthorized. Please check your credentials"
                403 -> "Forbidden. Access is denied"
                404 -> "Resource not found"
                500 -> "Internal Server Error. Please try again later"
                503 -> "Service Unavailable. Please try again later"
                else -> "Unexpected HTTP Error: $statusCode"
            }
        }

        is JsonParseException, is MalformedJsonException -> "Malformed JSON received. Paring failed"
        is IllegalArgumentException -> "Invalid argument provided. ${exception.message}"
        is IllegalStateException -> "Illegal application state. ${exception.message}"
        else -> "Unexpected error occurred: ${exception.message}"
    }
    return Resource.Error(message, exception)
}