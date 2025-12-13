package com.nat.greco.screens.home.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val date: String,
    val id: Int,
    val name: String,
    val routes: List<Route>
)

@Serializable
data class Route(
    val customer_id: CustomerId,
    val id: Int,
    val is_collected: Boolean,
    val is_visited: Boolean,
    val not_collected_reason_id: String,
    val not_visited_reason_id: String,
    val note: String,
    val order_amount: Double,
    val payment_amount: Double
)

@Serializable
data class CustomerId(
    val address: String,
    val contract: String,
    val email: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val phone: String,
    val ref: String,
    val credit_limit: Double,
    val create_date: String,
    val note: String,
    val is_active: Boolean,
    val tags: List<String>,
)