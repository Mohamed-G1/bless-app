package com.nat.bless.screens.promotionsList.domain.models

data class PromotionResponse(
    val date_from: String,
    val date_to: String,
    val description: String,
    val id: Int,
    val name: String
)