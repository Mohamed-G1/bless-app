package com.nat.greco.screens.receviceStock.domain.models

data class ReceiveStockResponse(
    val destination_location: String,
    val id: Int,
    val lines: List<Line>,
    val name: String,
    val origin: String,
    val partner: String,
    val picking_type: String,
    val scheduled_date: String,
    val source_location: String,
    val state: String
)

data class CategoryId(
    val id: Int,
    val name: String
)

data class UomPrice(
    val price: Double,
    val uom_id: Int,
    val uom_name: String
)

data class BasicUom(
    val uom_id: Int,
    val uom_name: String
)

data class Line(
    val id: Int,
    val product: Product,
    val quantity: Double,
    val uom: String
)

data class Product(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)