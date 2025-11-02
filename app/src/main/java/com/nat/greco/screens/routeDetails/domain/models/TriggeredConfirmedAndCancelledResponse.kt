package com.nat.greco.screens.routeDetails.domain.models

data class TriggeredConfirmedAndCancelledResponse(
    val id: Any,
    val jsonrpc: String,
    val result: Result
)
data class Result(
    val code: Int,
    val `data`: Data,
    val message: String
)
class Data(
    val message: String
)