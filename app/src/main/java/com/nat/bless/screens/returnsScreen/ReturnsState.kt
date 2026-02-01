package com.nat.bless.screens.returnsScreen

import com.nat.bless.screens.orders.domain.models.OrdersResponse

data class ReturnsState(
    val isLoading : Boolean = false,
    val navigateBack : Boolean = false,
    val error : String = "",
    val model : OrdersResponse? = null,
    val productId : Int = 0,
    val lines : List<Lines> = listOf(),
    val orderId : Int = 0,
    val counter : Int = 0
)
