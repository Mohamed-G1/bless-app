package com.nat.greco.screens.dayDetails.presentation

sealed class DayDetailsEvents {
    data object EndData : DayDetailsEvents()
    data class DataChanged(val date: String) : DayDetailsEvents()
}