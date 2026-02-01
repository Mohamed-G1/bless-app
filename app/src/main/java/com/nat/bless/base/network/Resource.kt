package com.nat.bless.base.network

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val message: String,
        val exception: Throwable? = null,
        val code: Int? = null,
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}