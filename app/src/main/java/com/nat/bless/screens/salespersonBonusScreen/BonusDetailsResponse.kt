package com.nat.bless.screens.salespersonBonusScreen

data class BonusDetailsResponse(
    val code: Int,
    val `data`: Data,
    val message: String
)

data class Data(
    val month: String,
    val records: List<Record>,
    val total_bonus: Double
)

data class Record(
    val amount: Double,
    val customer: String,
    val date: String,
    val note: String,
    val sale_order: String,
    val type: String
)