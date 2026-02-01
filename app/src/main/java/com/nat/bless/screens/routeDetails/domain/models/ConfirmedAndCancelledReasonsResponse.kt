package com.nat.bless.screens.routeDetails.domain.models

data class ConfirmedAndCancelledReasonsResponse(
    val code: Int,
    val `data`: List<ConfirmedAndCancelledReasonsData>,
    val message: String
)

data class ConfirmedAndCancelledReasonsData(
    val id: Int,
    val name: String
)