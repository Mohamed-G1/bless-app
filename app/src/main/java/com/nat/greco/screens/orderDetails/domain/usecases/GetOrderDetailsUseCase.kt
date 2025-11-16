package com.nat.greco.screens.orderDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest

class GetOrderDetailsUseCase(
    private val repository: OrderDetailsRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrderDetailsRequest>) =
        repository.getOrderDetails(request)
}