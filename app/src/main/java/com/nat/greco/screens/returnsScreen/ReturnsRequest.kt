package com.nat.greco.screens.returnsScreen

data class ReturnsRequest(
    val token: String,
    val order_id: Int,
    val lines: List<Lines>
)

data class Lines(
    val product_id: Int,
    val quantity: Double
)