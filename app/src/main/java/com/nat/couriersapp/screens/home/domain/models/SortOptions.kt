package com.nat.couriersapp.screens.home.domain.models

enum class SortOptions(val value: String) {
    FromLatestToOldest("من الأحدث للأقدم"),
    FromOldestToLatest("من الأقدم للأحدث"),
    HighestAmount("أعلي مبلغ تحصيل"),
    LowestAmount("أقل مبلغ تحصيل")
}