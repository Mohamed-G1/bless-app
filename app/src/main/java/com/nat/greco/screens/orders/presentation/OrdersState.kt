package com.nat.greco.screens.orders.presentation

import com.nat.greco.screens.orders.domain.models.OrdersResponse
import com.nat.greco.screens.orders.domain.models.ReturnsResponse

data class OrdersState(
    val isLoading : Boolean = false,
    val model : List<OrdersResponse> = listOf(),
    val returnsModel : List<ReturnsResponse> = listOf(),
    val error : String = ""
)
