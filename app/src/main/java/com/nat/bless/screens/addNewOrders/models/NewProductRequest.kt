package com.nat.bless.screens.addNewOrders.models

data class NewProductRequest(
    val token: String,
    val customer_id: Int? = null
)
