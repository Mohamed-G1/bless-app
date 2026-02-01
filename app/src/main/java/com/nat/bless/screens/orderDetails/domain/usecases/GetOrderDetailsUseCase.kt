package com.nat.bless.screens.orderDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest

class GetOrderDetailsUseCase(
    private val repository: OrderDetailsRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrderDetailsRequest>) =
        repository.getOrderDetails(request)
}