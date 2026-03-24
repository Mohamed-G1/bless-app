package com.nat.bless.screens.login.domain.models

import kotlinx.serialization.Serializable

data class LoginResponse(
    val bonus_summary: BonusSummary,
    val email: String,
    val mobile: String,
    val name: String,
    val target: Target,
    val token: String
)

data class BonusSummary(
    val details: List<Detail>,
    val total_this_month: Double
)

data class Detail(
    val count: Int,
    val total: Double,
    val type: String
)

@Serializable
data class Target(
    val achievement_rate: Double,
    val consumed: Double,
    val monthly_target: Double,
    val remaining: Double,
    val target_level: String
)