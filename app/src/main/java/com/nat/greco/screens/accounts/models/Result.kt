package com.nat.greco.screens.accounts.models

data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val total_remaining_amount: Double
)