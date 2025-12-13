package com.nat.greco.screens.dailyReport


sealed class DailyReportEvents {
    data class DataChanged(val date: String) : DailyReportEvents()

}