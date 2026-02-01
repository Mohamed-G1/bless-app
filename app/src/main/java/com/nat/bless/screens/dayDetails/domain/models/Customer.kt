package com.nat.bless.screens.dayDetails.domain.models

data class Customer(
    val address: String,
    val area_id: String,
    val area_name: String,
    val city_id: String,
    val city_name: String,
    val contract: String,
    val country_id: String,
    val country_name: String,
    val create_date: String,
    val credit_limit: Double,
    val distinctive_mark: String,
    val email: String,
    val id: Int,
    val is_active: Boolean,
    val mobile: String,
    val name: String,
    val note: String,
    val phone: String,
    val ref: String,
    val state_id: String,
    val state_name: String,
    val tags: List<String>
)