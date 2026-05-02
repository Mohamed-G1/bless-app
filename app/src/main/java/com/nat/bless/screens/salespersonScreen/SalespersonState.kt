package com.nat.bless.screens.salespersonScreen

import com.nat.bless.screens.login.domain.models.Detail

data class SalespersonState(
    val name: String = "",
//    val totalBonus: Double = 0.0,
//    val achievementRate: Double = 0.0,
//    val consumed: Double = 0.0,
//    val monthlyTarget: Double = 0.0,
//    val remaining: Double = 0.0,
//    val targetLevel: String = "",
//    val details: List<Detail> = emptyList(),
    val isLoading : Boolean  = false,
    val error : String  = "",
    val model : SalesPersonTargetResponse? = null
)
