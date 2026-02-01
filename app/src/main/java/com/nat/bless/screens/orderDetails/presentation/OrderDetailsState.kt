package com.nat.bless.screens.orderDetails.presentation

import com.nat.bless.screens.orders.domain.models.OrdersResponse

data class OrderDetailsState(
    val loading: Boolean = false,
    val model: OrdersResponse? = null,
    val error: String = ""
)
