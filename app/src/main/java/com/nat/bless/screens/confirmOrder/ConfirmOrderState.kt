package com.nat.bless.screens.confirmOrder

import com.nat.bless.screens.orders.domain.models.OrdersResponse

data class ConfirmOrderState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val navigateToCollectScreen: Boolean = false,
    val error: String = "",
    val model : OrdersResponse? = null,
    val orderId : Int = 0,
    val customerid : Int = 0
)
