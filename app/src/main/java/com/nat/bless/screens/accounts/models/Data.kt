package com.nat.bless.screens.accounts.models

data class Data(
    val amount_tax: Double,
    val amount_total: Double,
    val amount_untaxed: Double,
    val id: Int,
    val invoice_date: String,
    val name: String,
    val payment_state: String,
    val remaining_amount: Double,
    val source: String,
    val state: String
)