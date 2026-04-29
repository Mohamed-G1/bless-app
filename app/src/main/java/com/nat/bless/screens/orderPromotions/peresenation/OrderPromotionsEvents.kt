package com.nat.bless.screens.orderPromotions.peresenation

import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct

sealed class OrderPromotionsEvents {

    data class GetOrderPromotions(val customerId: Int) : OrderPromotionsEvents()
    data class ApplyPromotion(val selectedItems : List<AllowedProduct>) : OrderPromotionsEvents()

}