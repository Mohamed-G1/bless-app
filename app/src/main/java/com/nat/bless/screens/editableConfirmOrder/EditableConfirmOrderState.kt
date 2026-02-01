package com.nat.bless.screens.editableConfirmOrder

import com.nat.bless.screens.orders.domain.models.OrdersResponse

data class EditableConfirmOrderState(
    val isLoading : Boolean = false,
    val customerId : Int = 0,
    val error : String = "",
    val model : OrdersResponse? = null,

    )
