package com.nat.bless.screens.orderPromotions.domain.models


data class ApplyPromotionsRequest(
    val token: String,
    val customer_id: Int,
    val products: List<AppliedProducts>

)

data class AppliedProducts(
    val product_id: Int,
    val qty: Int
)