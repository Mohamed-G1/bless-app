package com.nat.greco.screens.dayDetails.domain.models

data class DayDetailsResponse(
    val id: Any,
    val jsonrpc: String,
    val result: Result
)

data class Data(
    val collected_amount: Double,
    val customer: Customer,
    val returned_qty: Int,
    val sold_qty: Double
)


data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)


data class Customer(
    val address: String,
    val contract: String,
    val email: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val phone: String
)