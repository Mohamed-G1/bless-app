package com.nat.greco.screens.addNewClient.domain.models

data class AddCustomerRequest(
    val customer_data: CustomerData,
    val token: String
)

data class CustomerData(
    val address: String,
    val contract: String,
    val email: String,
    val mobile: String,
    val name: String,
    val phone: String
)