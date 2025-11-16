package com.nat.greco.screens.stocks.models.returnsModel

data class ReturnedListData(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val quantity: Double,
    val reference: String,
    val uom_prices: List<UomPrice>
)