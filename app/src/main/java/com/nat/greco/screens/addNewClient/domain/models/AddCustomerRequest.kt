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
    val phone: String,

    val country_id : Int,
    val state_id : Int,
    val city_id : Int,
    val area_id : Int,
    val distinctive_mark : String,
    val location_length : String,
    val location_circles : String,
)

