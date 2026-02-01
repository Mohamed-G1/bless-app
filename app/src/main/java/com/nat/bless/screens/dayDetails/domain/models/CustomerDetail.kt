package com.nat.bless.screens.dayDetails.domain.models

data class CustomerDetail(
    val collected_amount: Int,
    val customer: Customer,
    val returned_qty: Int,
    val sold_qty: Double
)