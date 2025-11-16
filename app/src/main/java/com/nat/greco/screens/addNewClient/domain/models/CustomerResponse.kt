package com.nat.greco.screens.addNewClient.domain.models

data class CustomerResponse(
    val address: String,
    val contract: String,
    val email: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val phone: String
)