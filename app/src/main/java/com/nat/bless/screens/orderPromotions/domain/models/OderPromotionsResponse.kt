package com.nat.bless.screens.orderPromotions.domain.models

data class OderPromotionsResponse(
    val allowed_products: List<AllowedProduct>,
    val bonus_percentage: Double,
    val remaining_bonus_amount: Double
)

data class AllowedProduct(
    val id: Int,
    val name: String,
    val price: Double
)

