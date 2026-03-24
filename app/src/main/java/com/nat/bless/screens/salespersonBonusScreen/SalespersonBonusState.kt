package com.nat.bless.screens.salespersonBonusScreen

data class SalespersonBonusState(
    val isLoading: Boolean = false,
    val bonusDetails: BonusDetailsResponse? = null,
    val error: String? = null,
    val month: String? = null
)
