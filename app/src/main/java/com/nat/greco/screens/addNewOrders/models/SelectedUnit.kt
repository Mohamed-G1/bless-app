package com.nat.greco.screens.addNewOrders.models

import kotlinx.serialization.Serializable

@Serializable
data class SelectedUnit(
    val productId: Int,
    val uomId: Int,
    val uomName: String,
    val price: Double,
    val quantity: Int
)
