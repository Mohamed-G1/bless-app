package com.nat.greco.screens.addNewOrders.models

data class AddToCartResponse(
    val code: Int,
    val `data`: AddToCartData,
    val message: String
)

class AddToCartData