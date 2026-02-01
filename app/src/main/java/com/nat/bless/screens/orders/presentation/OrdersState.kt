package com.nat.bless.screens.orders.presentation

import com.nat.bless.screens.orders.domain.models.OrdersResponse
import com.nat.bless.screens.orders.domain.models.ReturnsResponse

data class OrdersState(
    val isLoading : Boolean = false,
    val model : List<OrdersResponse> = listOf(),
    val returnsModel : List<ReturnsResponse> = listOf(),
    val error : String = ""
)
