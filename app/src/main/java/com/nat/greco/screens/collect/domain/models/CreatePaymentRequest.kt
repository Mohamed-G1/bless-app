package com.nat.greco.screens.collect.domain.models

data class CreatePaymentRequest(
    val token: String,
    val customer_id: Int,
    val journal_id: Int,
    val amount: Double,
    val note: String,
)
