package com.nat.greco.screens.promotionsList.presentation

import com.nat.greco.screens.promotionsList.domain.models.PromotionResponse

data class PromotionState(
    val isLoading : Boolean = false,
    val error : String = "",
    val model : List<PromotionResponse> = listOf()
)
