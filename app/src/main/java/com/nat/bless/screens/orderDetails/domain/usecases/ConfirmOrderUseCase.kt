package com.nat.bless.screens.orderDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.repository.OrdersRepository

class ConfirmOrderUseCase(
    private val repository: OrdersRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrderDetailsRequest>) =
        repository.confirmOrder(request)
}