package com.nat.greco.screens.orderHistory

import com.nat.greco.screens.orders.domain.models.OrdersResponse

data class OrderHistoryDetailsState(
    val model : OrdersResponse? = null,
    val isLoading : Boolean = false,
    val error : String = ""

)
