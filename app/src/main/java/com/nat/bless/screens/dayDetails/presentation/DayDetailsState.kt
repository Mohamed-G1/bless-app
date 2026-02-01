package com.nat.bless.screens.dayDetails.presentation

import com.nat.bless.screens.dayDetails.domain.models.DayDetailsResponse

data class DayDetailsState(
    val isLoading: Boolean = false,
    val back: Boolean = false,
    val error: String= "",
    val model: DayDetailsResponse? = null,
    val date : String? = null
)
