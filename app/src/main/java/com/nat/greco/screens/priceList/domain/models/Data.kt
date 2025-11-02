package com.nat.greco.screens.priceList.domain.models

data class Data(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)