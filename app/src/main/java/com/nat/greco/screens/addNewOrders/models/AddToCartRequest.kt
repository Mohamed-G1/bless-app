package com.nat.greco.screens.addNewOrders.models

data class AddToCartRequest(
    val customer_id: Int,
    val lines: List<Line>,
    val token: String
)


data class Line(
    val price: Double,
    val product_id: Int,
    val quantity: Int,
    val uom_id: Int
)