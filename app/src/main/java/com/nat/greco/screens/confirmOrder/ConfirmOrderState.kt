package com.nat.greco.screens.confirmOrder

import com.nat.greco.screens.orders.domain.models.OrdersResponse

data class ConfirmOrderState(
    val isLoading: Boolean = false,
    val error: String = "",
    val model : OrdersResponse? = null,
    val orderId : Int = 0
)
