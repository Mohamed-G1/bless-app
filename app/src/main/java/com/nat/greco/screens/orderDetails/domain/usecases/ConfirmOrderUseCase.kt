package com.nat.greco.screens.orderDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.orders.domain.repository.OrdersRepository

class ConfirmOrderUseCase(
    private val repository: OrdersRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrderDetailsRequest>) =
        repository.confirmOrder(request)
}