package com.nat.bless.screens.orders.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.orders.domain.models.OrdersRequest
import com.nat.bless.screens.orders.domain.repository.OrdersRepository

class GetOrdersListUseCase(
    private val repository: OrdersRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrdersRequest>) =
        repository.getOrdersList(request)

}