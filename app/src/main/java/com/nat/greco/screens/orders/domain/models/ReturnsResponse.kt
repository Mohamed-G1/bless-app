package com.nat.greco.screens.orders.domain.models

data class ReturnsResponse(
    val destination_location: String,
    val id: Int,
    val lines: List<Lines>,
    val name: String,
    val origin: String,
    val partner: String,
    val picking_type: String,
    val scheduled_date: String,
    val source_location: String,
    val state: String
)

data class BasicUoms(
    val uom_id: Int,
    val uom_name: String
)

data class CategoryIds(
    val id: Int,
    val name: String
)

data class Lines(
    val id: Int,
    val product: Product,
    val quantity: Double,
    val uom: String
)

data class Product(
    val barcode: String,
    val basic_uom: BasicUoms,
    val category_id: CategoryIds,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrices>
)

data class UomPrices(
    val price: Double,
    val uom_id: Int,
    val uom_name: String
)