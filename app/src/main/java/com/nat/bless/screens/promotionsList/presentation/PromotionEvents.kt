package com.nat.bless.screens.promotionsList.presentation

sealed class PromotionEvents {
    data class CustomerIdChanged(val id: Int) : PromotionEvents()
}