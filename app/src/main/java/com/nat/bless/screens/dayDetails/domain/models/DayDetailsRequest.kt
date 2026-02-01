package com.nat.bless.screens.dayDetails.domain.models

data class DayDetailsRequest(
    val date: String,
    val token: String,
    val route_id : Int

)