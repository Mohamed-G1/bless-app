package com.nat.bless.screens.orderPromotions.peresenation

import com.nat.bless.screens.orderPromotions.domain.models.OderPromotionsResponse

data class OrderPromotionsState(
    val isLoading: Boolean = false,
    val canNavigateBack: Boolean = false,
    val errorMessage: String = "",
    val model : OderPromotionsResponse? = null,
    val customerId : Int = 0
)
