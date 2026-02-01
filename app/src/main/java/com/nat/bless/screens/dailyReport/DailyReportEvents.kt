package com.nat.bless.screens.dailyReport


sealed class DailyReportEvents {
    data class DataChanged(val date: String) : DailyReportEvents()

}