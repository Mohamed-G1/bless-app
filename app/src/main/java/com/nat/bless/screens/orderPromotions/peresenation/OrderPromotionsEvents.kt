package com.nat.bless.screens.orderPromotions.peresenation

import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct
import com.nat.bless.screens.orderPromotions.domain.models.AppliedProducts

sealed class OrderPromotionsEvents {

    data class GetOrderPromotions(val customerId: Int) : OrderPromotionsEvents()
    data class ApplyPromotion(val selectedItems : List<AppliedProducts>) : OrderPromotionsEvents()

}