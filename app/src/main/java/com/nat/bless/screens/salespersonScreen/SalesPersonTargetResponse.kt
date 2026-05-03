package com.nat.bless.screens.salespersonScreen

import kotlinx.serialization.Serializable

@Serializable
data class SalesPersonTargetResponse(
    val achievement_rate: Double,
    val consumed: Double,
    val monthly_target: Double,
    val remaining: Double,
    val target_level: String,
    val month: String
)
