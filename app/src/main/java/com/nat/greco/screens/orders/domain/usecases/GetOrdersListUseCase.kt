package com.nat.greco.screens.orders.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.orders.domain.models.OrdersRequest
import com.nat.greco.screens.orders.domain.repository.OrdersRepository

class GetOrdersListUseCase(
    private val repository: OrdersRepository
) {

    suspend operator fun invoke(request: BaseRequest<OrdersRequest>) =
        repository.getOrdersList(request)

}