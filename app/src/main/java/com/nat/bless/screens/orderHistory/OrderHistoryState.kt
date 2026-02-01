package com.nat.bless.screens.orderHistory

import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.bless.screens.routeDetails.domain.models.ProductId

data class OrderHistoryState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val model: List<OrderHistoryResponse> = listOf(),
    val discount: Double? = null,
    val id: Int? = null,
    val price_subtotal: Double? = null,
    val price_unit: Double? = null,
    val product_id: ProductId? = null,
    val product_uom: String? = null,
    val product_uom_qty: Double? = null,
    val qty_delivered: Double? = null,
    val taxes: List<String>? = null,




    val amount_tax: Double? = null,
    val amount_total: Double? = null,
    val amount_untaxed: Double? = null,
    val date_order: String? = null,
    val order_id: Int? = null,
    val name: String? = null,
)
