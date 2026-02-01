package com.nat.bless.screens.addNewOrders.models.addTocart

data class ProductId(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)