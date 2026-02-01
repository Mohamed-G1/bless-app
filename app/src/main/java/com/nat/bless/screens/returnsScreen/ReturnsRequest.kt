package com.nat.bless.screens.returnsScreen

data class ReturnsRequest(
    val token: String,
    val order_id: Int,
    val lines: List<Lines>,
    val route_id : Int
)

data class Lines(
    val product_id: Int,
    val quantity: Double
)