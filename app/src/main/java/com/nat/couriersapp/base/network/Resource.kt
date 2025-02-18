package com.nat.couriersapp.base.network

sealed class Resource<out T> {
    data object Empty : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val message: String,
        val exception: Throwable? = null,
    ) : Resource<Nothing>()

    data class Loading(val status: Boolean) : Resource<Nothing>()
}