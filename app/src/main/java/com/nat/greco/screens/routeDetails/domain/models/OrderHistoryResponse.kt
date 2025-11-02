package com.nat.greco.screens.routeDetails.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryResponse(
    val amount_tax: Double,
    val amount_total: Double,
    val amount_untaxed: Double,
    val date_order: String,
    val id: Int,
    val name: String,
    val order_lines: List<OrderLine>
)