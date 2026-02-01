package com.nat.bless.screens.editableConfirmOrder.domain.models

data class DeleteOrderRequest(
    val line_id: Int,
    val token: String
)