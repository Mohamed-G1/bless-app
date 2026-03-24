package com.nat.bless.screens.addNewOrders.models

data class StockListData(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val product_id: Int,
    val name: String,
    val quantity: Double,
    val lot_id: Int,
    val lot_name: String,
    val image_url: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)