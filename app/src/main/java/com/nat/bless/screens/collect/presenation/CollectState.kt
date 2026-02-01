package com.nat.bless.screens.collect.presenation

import com.nat.bless.screens.collect.domain.models.CollectResponse

data class CollectState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val error: String = "",
    val notes: String = "",
    val model: List<CollectResponse> = emptyList(),

    val journalId: Int = 0,
    val customerId: Int = 0,
    val amount: Double? = 0.0,
    val totalDept: Double? = 0.0,
    val lastTotalOrder: Double? = 0.0,

    val isValidAmount : Boolean = true,
    val isValidJournalId : Boolean = true
)
