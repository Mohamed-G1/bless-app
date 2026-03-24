package com.nat.bless.screens.addNewOrders.models

import kotlinx.serialization.Serializable

@Serializable
data class SelectedUnit(
    val id: Int,
    val productId: Int,
    val uomId: Int,
    val uomName: String,
    val price: Double,
    val quantity: Int,
    val lot_id: Int,
    val lot_name: String,
)
