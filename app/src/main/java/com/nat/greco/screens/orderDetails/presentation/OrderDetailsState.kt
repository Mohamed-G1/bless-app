package com.nat.greco.screens.orderDetails.presentation

import com.nat.greco.screens.orders.domain.models.OrdersResponse

data class OrderDetailsState(
    val loading: Boolean = false,
    val model: OrdersResponse? = null,
    val error: String = ""
)
