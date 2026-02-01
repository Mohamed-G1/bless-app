package com.nat.bless.screens.editableConfirmOrder.domain.models

data class UpdateOrderRequest(
    val line_id: Int,
    val price: Double,
    val quantity: Int,
    val token: String
)