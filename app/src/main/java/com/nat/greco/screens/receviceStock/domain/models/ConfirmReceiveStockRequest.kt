package com.nat.greco.screens.receviceStock.domain.models

data class ConfirmReceiveStockRequest(
    val token: String,
    val transfer_id: Int
)
