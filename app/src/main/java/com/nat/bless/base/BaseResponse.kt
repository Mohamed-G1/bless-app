package com.nat.bless.base

data class BaseResponse<T>(
    val jsonrpc: String,
    val id: Any?,
    val result: Result<T>
)

data class Result<T>(
    val code: Int,
    val data: T,
    val message: String
)
