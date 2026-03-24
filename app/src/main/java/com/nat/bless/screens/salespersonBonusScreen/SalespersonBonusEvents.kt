package com.nat.bless.screens.salespersonBonusScreen

interface SalespersonBonusEvents {
    data class OnMonthSelected(val month: String) : SalespersonBonusEvents
}