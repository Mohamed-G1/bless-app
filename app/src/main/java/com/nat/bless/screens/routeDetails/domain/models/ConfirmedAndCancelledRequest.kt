package com.nat.bless.screens.routeDetails.domain.models

data class ConfirmedAndCancelledRequest(
    val token: String,
    val route_id: Int,
    val not_visited_reason_id: Int
)
