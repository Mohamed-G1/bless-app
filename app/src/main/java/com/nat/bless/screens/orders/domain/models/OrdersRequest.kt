package com.nat.bless.screens.orders.domain.models

data class OrdersRequest(
    val token: String,

    val month : String? = null,
    val year : String? = null
)
