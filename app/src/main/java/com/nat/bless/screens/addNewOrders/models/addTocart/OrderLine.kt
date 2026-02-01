package com.nat.bless.screens.addNewOrders.models.addTocart

data class OrderLine(
    val discount: Double,
    val id: Int,
    val price_subtotal: Double,
    val price_unit: Double,
    val product_id: ProductId,
    val product_uom: String,
    val product_uom_qty: Double,
    val qty_delivered: Double,
    val taxes: List<String>
)