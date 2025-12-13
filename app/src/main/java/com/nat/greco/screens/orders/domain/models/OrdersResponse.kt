package com.nat.greco.screens.orders.domain.models

import com.nat.greco.screens.home.domain.models.CustomerId
import kotlinx.serialization.Serializable

@Serializable
data class OrdersResponse(
    val amount_tax: Double,
    val amount_total: Double,
    val amount_untaxed: Double,
    val date_order: String,
    val id: Int,
    val customer_id: CustomerId,
    val name: String,
    val order_lines: List<OrdersLine>

)

@Serializable
data class OrdersLine(
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

@Serializable
data class CategoryId(
    val id: Int,
    val name: String
)

@Serializable
data class BasicUom(
    val uom_id: Int,
    val uom_name: String
)

@Serializable
data class ProductId(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)

@Serializable
data class UomPrice(
    val price: Double,
    val uom_id: Int,
    val uom_name: String
)