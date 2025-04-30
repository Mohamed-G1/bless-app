package com.nat.greco.screens.home.domain.models

enum class SortOptions(val value: String) {
    FromLatestToOldest("من الأحدث للأقدم"),
    FromOldestToLatest("من الأقدم للأحدث"),
    HighestAmount("أعلي مبلغ تحصيل"),
    LowestAmount("أقل مبلغ تحصيل")
}