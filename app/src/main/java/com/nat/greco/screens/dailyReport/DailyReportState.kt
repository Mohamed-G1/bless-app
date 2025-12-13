package com.nat.greco.screens.dailyReport

import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse

data class DailyReportState(
    val isLoading: Boolean = false,
    val error: String= "",
    val model: DayDetailsResponse? = null,
    val date : String? = null
)
