package com.nat.bless.screens.addNewOrders.models.addTocart

data class Data(
    val amount_tax: Double,
    val amount_total: Double,
    val amount_untaxed: Double,
    val date_order: String,
    val id: Int,
    val name: String,
    val order_lines: List<OrderLine>
)