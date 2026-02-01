package com.nat.bless.screens.orderHistory

import com.nat.bless.screens.orders.domain.models.OrdersResponse

data class OrderHistoryDetailsState(
    val model : OrdersResponse? = null,
    val isLoading : Boolean = false,
    val error : String = ""

)
