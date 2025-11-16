package com.nat.greco.screens.orders.presentation

import com.nat.greco.screens.orders.domain.models.OrdersResponse

data class OrdersState(
    val isLoading : Boolean = false,
    val model : List<OrdersResponse> = listOf(),
    val error : String = ""
)
