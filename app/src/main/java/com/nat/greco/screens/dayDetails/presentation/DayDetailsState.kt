package com.nat.greco.screens.dayDetails.presentation

import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse

data class DayDetailsState(
    val isLoading: Boolean = false,
    val error: String= "",
    val model: DayDetailsResponse? = null,
    val date : String? = null
)
